package co.tripzii.station


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.adapter.TimelineAdapter
import co.tripzii.station.adapter.TripAdapter
import co.tripzii.station.model.ImageModel
import co.tripzii.station.model.TripModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_trip_details.*
import kotlinx.android.synthetic.main.slider_images_layout.*
import kotlinx.android.synthetic.main.trip_item.view.*

class TripDetailsActivity : AppCompatActivity() {

    lateinit var timelineAdapter: TimelineAdapter
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageList = arrayListOf<ImageModel>()
    private var tripModel : TripModel? = null
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)
        tripModel = intent?.getParcelableExtra("trip") as? TripModel
        Log.d("alltrip", tripModel.toString())
        bindDataTripDetails(tripModel)
        if (tripModel?.timeline != null) {
            timelineAdapter = TimelineAdapter(tripModel?.timeline!!)
            timelineRecyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            timelineRecyclerView.adapter = timelineAdapter
        }
        val trip: MutableList<TripModel> = mutableListOf()
        val viewFlipper = findViewById<ViewFlipper>(R.id.tripDetailsImageView)
        if (viewFlipper != null) {
            viewFlipper.setInAnimation(applicationContext, android.R.anim.slide_in_left)
            viewFlipper.setOutAnimation(applicationContext, android.R.anim.slide_out_right)
        }
        if (viewFlipper != null){
            for (image in imageList) {
                val imageView = ImageView(this)
                val layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                layoutParams.setMargins(10, 10, 10, 10)
                layoutParams.gravity = Gravity.CENTER
                imageView.layoutParams = layoutParams
                viewFlipper.addView(imageView)
            }
        }
        Picasso.get().load("image")
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
                val interesthingTripAdapter = TripAdapter(trip, onSelectItem = {trip ->
                    Log.d("interesthingTrip", trip.toString())
                    val intent = Intent(this@TripDetailsActivity,TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                })
                interesthingTripRecycleView.layoutManager = LinearLayoutManager(
                    this,
                    RecyclerView.HORIZONTAL, false
                )
                interesthingTripRecycleView.adapter = interesthingTripAdapter
                interesthingTripAdapter.notifyDataSetChanged()
            }
    }
    fun bindDataTripDetails(trip: TripModel?) {
        tripNameDetailsTextView.text = trip?.nametrip
        tripDetailsLocationTextView.text = trip?.provice
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
    }
}



