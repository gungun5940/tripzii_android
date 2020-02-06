package co.tripzii.station

//import co.tripzii.station.adapter.TripDetailsAdapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
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
    var tripDetail : TripDeailsDAO? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

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
