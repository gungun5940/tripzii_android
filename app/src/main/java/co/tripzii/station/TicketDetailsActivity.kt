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
import co.tripzii.station.adapter.TicketTimelineAdapter
import co.tripzii.station.adapter.TripTicketAdapter
import co.tripzii.station.model.TicketModel
import co.tripzii.station.ui.ticket.TicketFragment
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
import kotlinx.android.synthetic.main.activity_ticket_details.*
import kotlinx.android.synthetic.main.ticket_booking_bottom_bar.*
import kotlinx.android.synthetic.main.ticket_description.*
import kotlinx.android.synthetic.main.ticket_duration.*
import kotlinx.android.synthetic.main.ticket_include.*
import kotlinx.android.synthetic.main.ticket_interesting.*
import kotlinx.android.synthetic.main.ticket_remark.*

class TicketDetailsActivity : AppCompatActivity() , OnMapReadyCallback {

    lateinit var ticketTimelineAdapter: TicketTimelineAdapter

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var ticketModel: TicketModel? = null

    private val progressBar = ProgressBarActivity()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_details)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        ticketDetailsBackToHomePageButton.setOnClickListener {
            val intent = Intent(this.applicationContext, TicketFragment ::class.java)
            startActivity(intent)
            progressBar.show(this, "Please Wait...")
            Handler().postDelayed({}, 2000)
        }
        ticketBookingButton.setOnClickListener {
            val intent = Intent(this@TicketDetailsActivity , BookingTicketActivity::class.java)
            startActivity(intent)
            progressBar.show(this, "Booking...")
            Handler().postDelayed({}, 2000)
        }
        ticketModel = intent?.getParcelableExtra("ticket") as? TicketModel
        Log.d("TicketDetail: " ,  ticketModel.toString())
        bindDataTransferDetails(ticketModel)
        if (ticketModel?.ticketTimeline != null) {
            ticketTimelineAdapter = TicketTimelineAdapter(ticketModel?.ticketTimeline!!)
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            ticketTimelineRecyclerView.adapter = ticketTimelineAdapter
        }
        val ticket: MutableList<TicketModel> = mutableListOf()
        val mViewFlipper = findViewById<ViewFlipper>(R.id.ticketDetailsImageViewFlipper)
        ticketDetailsArrowLeftButton.setOnClickListener {
            mViewFlipper.setInAnimation(this, R.anim.slide_in_rigth)
            mViewFlipper.setOutAnimation(this, R.anim.slide_out_left)
            mViewFlipper.showPrevious()
        }
        ticketDetailsArrowRightButton.setOnClickListener {
            mViewFlipper.setInAnimation(this,R.anim.slide_in_left)
            mViewFlipper.setOutAnimation(this,R.anim.slide_out_rigth)
            mViewFlipper.showNext()
        }
        if (mViewFlipper != null) {
            if (ticketModel?.image != null) {
                for (image in ticketModel?.image!!) {
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
        val getData = db.collection("attraction ticket")
        getData.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TicketModel::class.java).apply {
                        ticketId = ds.document.id
                    }
                    when (ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = ticket.filter { it.ticketId == item.ticketId }
                            if (items.isEmpty()) item.let { ticket.add(it) }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val index = ticket.indexOfFirst { it.ticketId == item.ticketId }
                            item.apply { ticket[index] = this }
                        }
                        else -> {
                        }
                    }
                }
                val interestingTripTicketAdapter = TripTicketAdapter(ticket , onSelectItem = { ticket ->
                    Log.d("interesting transfer service", ticket.toString())
                    val intent = Intent(this@TicketDetailsActivity,
                        TicketDetailsActivity::class.java)
                    intent.putExtra("ticket", ticket)
                    startActivity(intent)
                    progressBar.show(this, "Please Wait...")
                    Handler().postDelayed({}, 2000)

                })
                interestingTripTicketRecycleView.layoutManager = LinearLayoutManager(
                    this,
                    RecyclerView.HORIZONTAL, false
                )
                interestingTripTicketRecycleView.adapter = interestingTripTicketAdapter
                interestingTripTicketAdapter.notifyDataSetChanged()
            }
    }
    private fun bindDataTransferDetails(ticket: TicketModel?) {
        ticketNameDetailsTextView.text = ticket?.nameticket
        ticketDetailsLocationTextView.text = ticket?.province
        ticketRateTextView.text = ticket?.rate
        ticketPriceTextView.text = ticket?.price
        ticketDayTextView.text = ticket?.countDate
        ticketDescriptionTextView.text = ticket?.description
        ticketScheduleTextView.text = ticket?.durationSchedule
        ticketDurationTextView.text = ticket?.duration
        ticketPartyTypeTextView.text = ticket?.durationType
        ticketIncludeTextView.text = ticket?.include
        ticketRemarkTextView.text = ticket?.remark
        ticketTotalPriceTextView.text = ticket?.price
        ticketFoodTextView.text = ticket?.serviceFood
        ticketPickupTextView.text = ticket?.servicePickup
        ticketGuideTextView.text = ticket?.serviceGuide
        ticketAccidentTextView.text = ticket?.serviceAccident
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        if (mMap != checkNotNull(mMap)) {
            val location =
                LatLng(ticketModel?.latitude!!.toDouble(), ticketModel?.longitude!!.toDouble())
            mMap.addMarker(MarkerOptions().position(location))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f))
            mMap.isMyLocationEnabled
            try {
                val success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.maps
                    )
                )
                if (!success) {
                    Log.e("MapsActivity", "Style parsing failed.")
                }
            } catch (e: Resources.NotFoundException) {
                Log.e("MapsActivity", "Can't find style. Error: ", e)
            }
        }
    }
//}
