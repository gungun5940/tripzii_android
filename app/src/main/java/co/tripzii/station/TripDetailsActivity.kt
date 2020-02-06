package co.tripzii.station

//import co.tripzii.station.adapter.TripDetailsAdapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.tripzii.station.adapter.TimelineAdapter
import co.tripzii.station.model.TimelineDAO
import co.tripzii.station.model.TripDeailsDAO
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_trip_details.*

class TripDetailsActivity : AppCompatActivity() {

    lateinit var timelineAdapter: TimelineAdapter
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var imageList = intArrayOf(R.drawable.forest, R.drawable.p2, R.drawable.p3)
    var tripDetail : TripDeailsDAO? = null


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        val viewFlipper = findViewById<ViewFlipper>(R.id.tripDetailsImageView)

        if (viewFlipper != null) {
            viewFlipper.setInAnimation(applicationContext, android.R.anim.slide_in_left)
            viewFlipper.setOutAnimation(applicationContext, android.R.anim.slide_out_right)
        }

        if (viewFlipper != null) {
            for (image in imageList) {
                val imageView = ImageView(this)
                val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT)
                layoutParams.setMargins(10, 10, 10, 10)
                layoutParams.gravity = Gravity.CENTER
                imageView.layoutParams = layoutParams
                imageView.setImageResource(image)
                viewFlipper.addView(imageView)
            }
        }





        val docRef = db.collection("alltrip")
            .document("01")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("get string", "c data: ${document}")
                    this.tripDetail = document.toObject(TripDeailsDAO::class.java)
                    Log.d("tripdetail",tripDetail?.timeline.toString())
                    bindDataTripDetails(this.tripDetail!!)
                    timelineAdapter = TimelineAdapter(this.tripDetail?.timeline!!)
                    timelineRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                    timelineRecyclerView.adapter = timelineAdapter
                } else {
                    Log.d("2", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("3", "get failed with ", exception)
            }
    }
    fun bindDataTripDetails(tripDeailsDAO: TripDeailsDAO){
        tripNameDetailsTextView.text = tripDeailsDAO.nametrip
        tripDetailsLocationTextView.text = tripDeailsDAO.provice
        tripRateTextView.text = tripDeailsDAO.rate
        tripPriceTextView.text = tripDeailsDAO.price
        tripDayTextView.text = tripDeailsDAO.countDate
        descriptionTextView.text = tripDeailsDAO.description
        scheduleTextView.text = tripDeailsDAO.durationSchedule
        durationTextView.text = tripDeailsDAO.duration
        partyTypeTextView.text = tripDeailsDAO.durationType
        includeTextView.text = tripDeailsDAO.include
        remarkTextView.text = tripDeailsDAO.remark
        totalPriceTextView.text = tripDeailsDAO.price
    }
}
