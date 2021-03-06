package co.tripzii.station

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.adapter.TimelineAdapter
import co.tripzii.station.adapter.TripAdapter
import co.tripzii.station.model.TripModel
import co.tripzii.station.ui.activities.ActivitiesFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_trip_details.*
import kotlinx.android.synthetic.main.booking_bottom_bar.*
import kotlinx.android.synthetic.main.trip_description.*
import kotlinx.android.synthetic.main.trip_duration.*
import kotlinx.android.synthetic.main.trip_include.*
import kotlinx.android.synthetic.main.trip_interesting.*
import kotlinx.android.synthetic.main.trip_remark.*


class TripDetailsActivity : AppCompatActivity() , OnMapReadyCallback {

    lateinit var timelineAdapter: TimelineAdapter

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var tripModel: TripModel? = null

    private val progressBar = ProgressBarActivity()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)
          val mapFragment = supportFragmentManager
              .findFragmentById(R.id.map) as SupportMapFragment
          mapFragment.getMapAsync(this)
        BackToHomePageButton.setOnClickListener {
                    val intent = Intent(this, AllTripActivity::class.java)
                    startActivity(intent)
                    progressBar.show(this, "Please Wait...")
                    Handler().postDelayed({}, 2000)
                }
                bookingButton.setOnClickListener {
                    val intent = Intent(this@TripDetailsActivity, BookingActivity::class.java)
                    startActivity(intent)
                    progressBar.show(this, "Booking...")
                    Handler().postDelayed({}, 2000)
                }
                tripModel = intent?.getParcelableExtra("trip") as? TripModel
                Log.d("TripDetail: " ,  tripModel.toString())
                bindDataTripDetails(tripModel)
                if (tripModel?.timeline != null) {
                    timelineAdapter = TimelineAdapter(tripModel?.timeline!!)
                    timelineRecyclerView.layoutManager =
                        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    timelineRecyclerView.adapter = timelineAdapter
                }
              val trip: MutableList<TripModel> = mutableListOf()
              val mViewFlipper = findViewById<ViewFlipper>(R.id.tripDetailsImageViewFlipper)
              arrowLeftButton.setOnClickListener {
                  mViewFlipper.setInAnimation(this, R.anim.slide_in_rigth)
                  mViewFlipper.setOutAnimation(this, R.anim.slide_out_left)
                  mViewFlipper.showPrevious()
              }
              arrowRightButton.setOnClickListener {
                  mViewFlipper.setInAnimation(this,R.anim.slide_in_left)
                  mViewFlipper.setOutAnimation(this,R.anim.slide_out_rigth)
                  mViewFlipper.showNext()
              }
                if (mViewFlipper != null) {
                    if (tripModel?.image != null) {
                        for (image in tripModel?.image!!) {
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
                val getData = db.collection("alltrip")
                getData.get()
                    .addOnCompleteListener { task ->
                        for (ds in task.result?.documentChanges!!) {
                            val item = ds.document.toObject(TripModel::class.java).apply {
                                tripId = ds.document.id
                            }
                            when (ds.type) {
                                DocumentChange.Type.ADDED -> {
                                    val items = trip.filter { it.tripId == item.tripId }
                                    if (items.isEmpty()) item.let { trip.add(it) }
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    val index = trip.indexOfFirst { it.tripId == item.tripId }
                                    item.apply { trip[index] = this }
                                }
                                else -> {
                                }
                            }
                        }
                        val interestingTripAdapter = TripAdapter(trip, onSelectItem = { trip ->
                            Log.d("interestingTrip", trip.toString())
                            val intent =
                                Intent(this@TripDetailsActivity, TripDetailsActivity::class.java)
                            intent.putExtra("trip", trip)
                            startActivity(intent)
                            progressBar.show(this, "Please Wait...")
                            Handler().postDelayed({}, 2000)

                        })
                        interestingTripRecycleView.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.HORIZONTAL, false
                        )
                        interestingTripRecycleView.adapter = interestingTripAdapter
                        interestingTripAdapter.notifyDataSetChanged()
                    }
            }
    private fun bindDataTripDetails(trip: TripModel?) {
        tripNameDetailsTextView.text = trip?.nametrip
        tripDetailsLocationTextView.text = trip?.province
        tripRateTextView.text = trip?.rate
        tripPriceTextView.text = trip?.price
        tripDayTextView.text = trip?.countDate
        descriptionTextView.text = trip?.description
        scheduleTextView.text = trip?.durationSchedule
        durationTextView.text = trip?.duration
        partyTypeTextView.text = trip?.durationType
        includeTextView.text = trip?.include
        remarkTextView.text = trip?.remark
        totalPriceTextView.text = trip?.price
        foodTextView.text = trip?.serviceFood
        pickupTextView.text = trip?.servicePickup
        guideTextView.text = trip?.serviceGuide
        accidentTextView.text = trip?.serviceAccident
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        if (mMap != checkNotNull(mMap)) {
            val location =
                LatLng(tripModel?.latitude!!.toDouble(), tripModel?.longitude!!.toDouble())
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


