package co.tripzii.station

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.adapter.TransferAdapter
import co.tripzii.station.adapter.TransferTimelineAdapter
import co.tripzii.station.model.TransferModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_transfer_details.*
import kotlinx.android.synthetic.main.tarnsfer_booking_bottom_bar.*
import kotlinx.android.synthetic.main.transfer_description.*
import kotlinx.android.synthetic.main.transfer_duration.*
import kotlinx.android.synthetic.main.transfer_include.*
import kotlinx.android.synthetic.main.transfer_interesting.*
import kotlinx.android.synthetic.main.transfer_remark.*


class TransferDetailsActivity : AppCompatActivity(), OnMapReadyCallback   {

    lateinit var transferTimelineAdapter: TransferTimelineAdapter

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var transferModel: TransferModel? = null

    private val progressBar = ProgressBarActivity()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_details)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        transferBackToHomePageButton.setOnClickListener {
            val intent = Intent(this@TransferDetailsActivity , AllTripActivity::class.java)
            startActivity(intent)
            progressBar.show(this, "Please Wait...")
            Handler().postDelayed({}, 2000)
        }
        transferBookingButton.setOnClickListener {
            val intent = Intent(this@TransferDetailsActivity , BookingActivity::class.java)
            startActivity(intent)
            progressBar.show(this, "Booking...")
            Handler().postDelayed({}, 2000)
        }
        transferModel = intent?.getParcelableExtra("transfer") as? TransferModel
        Log.d("TransferDetail: " ,  transferModel.toString())
        bindDataTransferDetails(transferModel)
        if (transferModel?.transferTimeline != null) {
            transferTimelineAdapter = TransferTimelineAdapter(transferModel?.transferTimeline!!)
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            transferTimelineRecyclerView.adapter = transferTimelineAdapter
        }
        val transfer: MutableList<TransferModel> = mutableListOf()
        val mViewFlipper = findViewById<ViewFlipper>(R.id.transferDetailsImageViewFlipper)
        transferArrowLeftButton.setOnClickListener {
            mViewFlipper.setInAnimation(this, R.anim.slide_in_rigth)
            mViewFlipper.setOutAnimation(this, R.anim.slide_out_left)
            mViewFlipper.showPrevious()
        }
        transferArrowRightButton.setOnClickListener {
            mViewFlipper.setInAnimation(this,R.anim.slide_in_left)
            mViewFlipper.setOutAnimation(this,R.anim.slide_out_rigth)
            mViewFlipper.showNext()
        }
        if (mViewFlipper != null) {
            if (transferModel?.image != null) {
                for (image in transferModel?.image!!) {
                    val imageView = ImageView(this)
                    val layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.gravity = Gravity.CENTER
                    imageView.layoutParams = layoutParams
                    Picasso.get().load(image.url).into(imageView)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    mViewFlipper.addView(imageView)
                }
            }
        }
        val getData = db.collection("transfer service")
        getData.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TransferModel::class.java).apply {
                        transferId = ds.document.id
                    }
                    when (ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = transfer.filter { it.transferId == item.transferId }
                            if (items.isEmpty()) item.let { transfer.add(it) }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val index = transfer.indexOfFirst { it.transferId == item.transferId }
                            item.apply { transfer[index] = this }
                        }
                        else -> {
                        }
                    }
                }
                val interestingTransferAdapter = TransferAdapter(transfer , onSelectItem = { transfer ->
                    Log.d("interesting transfer service", transfer.toString())
                    val intent =
                        Intent(this@TransferDetailsActivity
                            , TransferDetailsActivity::class.java)
                    intent.putExtra("transfer", transfer)
                    startActivity(intent)
                    progressBar.show(this, "Please Wait...")
                    Handler().postDelayed({}, 2000)

                })
                interestingTransferServiceRecycleView.layoutManager = LinearLayoutManager(
                    this,
                    RecyclerView.HORIZONTAL, false
                )
                interestingTransferServiceRecycleView.adapter = interestingTransferAdapter
                interestingTransferAdapter.notifyDataSetChanged()
            }
    }
    private fun bindDataTransferDetails(transfer: TransferModel?) {
        transferNameDetailsTextView.text = transfer?.nametransfer
        transferDetailsLocationTextView.text = transfer?.province
        transferRateTextView.text = transfer?.rate
        transferPriceTextView.text = transfer?.price
        transferDayTextView.text = transfer?.countDate
        transferDescriptionTextView.text = transfer?.description
        transferScheduleTextView.text = transfer?.durationSchedule
        transferDurationTextView.text = transfer?.duration
        transferPartyTypeTextView.text = transfer?.durationType
        transferIncludeTextView.text = transfer?.include
        transferRemarkTextView.text = transfer?.remark
        transferTotalPriceTextView.text = transfer?.price
        transferFoodTextView.text = transfer?.serviceFood
        transferServiceTextView.text = transfer?.serviceGuide
        transferAccidentTextView.text = transfer?.serviceAccident
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val location = LatLng(transferModel?.latitude!!.toDouble() , transferModel?.longitude!!.toDouble() )
        mMap.addMarker(MarkerOptions().position(location))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f))
        mMap.isMyLocationEnabled
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.maps))
            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("MapsActivity", "Can't find style. Error: ", e)
        }
    }
}
