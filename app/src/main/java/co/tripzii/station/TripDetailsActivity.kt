package co.tripzii.station

//import co.tripzii.station.adapter.TripDetailsAdapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.tripzii.station.adapter.TimelineAdapter
import co.tripzii.station.model.TimelineDAO
import co.tripzii.station.model.TripDeailsDAO
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_trip_details.*

class TripDetailsActivity : AppCompatActivity() {

    lateinit var adapter: TimelineAdapter
//    lateinit var adapter2: TripDetailsAdapter
    lateinit var action: View.OnClickListener
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val tripDetails = TripDeailsDAO()
    val timelines : MutableList<TimelineDAO> = mutableListOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)


        val timeline = db.collection("AllTrip")
            .document("01")
            .collection("Category")
            .document("001")
            .collection("Trip")
            .document("0001")
            .collection("timeline")

        timeline.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (ds in task.getResult()!!.getDocumentChanges()) {
                        val item = ds?.document?.toObject(TimelineDAO::class.java)?.apply {
                            timeEvent = ds.document.getString("time")
                            eventDetails = ds.document.getString("detail")
                        }
                        when(ds.type) {
                            DocumentChange.Type.ADDED -> {
//                                timelines.add(item!!)
                                val items = timelines.filter { it.timeEvent == item?.timeEvent }
                                if (items.isEmpty()) item.let { timelines.add(it!!) }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val index = timelines.indexOfFirst { it.timeEvent == item?.timeEvent }
                                item?.apply { timelines[index] = this }
                            }
                            else -> {

                            }
                        }
                    }
                    Log.d("timeline", timelines.toString())
                    val reportAdapter = TimelineAdapter(timelines)
                    timelineRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
                    timelineRecyclerView.adapter = reportAdapter
                }
            }
        val docRef = db.collection("AllTrip")
            .document("01")
            .collection("Category")
            .document("001")
            .collection("Trip")
            .document("0001")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("1", "c data: ${document}")
                    tripDetails.tripNameDetail = document.getString("nametrip")
                    tripDetails.triprating = document.getString("rate")
                    tripDetails.tripPrice = document.getString("price")
                    tripDetails.tripLocation = document.getString("location")
                    tripDetails.tripPartyType = document.getString("partytype")
                    tripDetails.tripDay = document.getString("duration")
                    tripDetails.tripSchedule = document.getString("schedule")
                    tripDetails.tripRemark= document.getString("remark")
                    tripDetails.tripDesc = document.getString("description")
                    tripDetails.duration = document.getString("durationtype")
                    tripDetails.tripInclude = document.getString("include")
//                    tripDetails.timeline = document.getString("timeline")
                    bindDataTripDetails(tripDetails)
                } else {
                    Log.d("2", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("3", "get failed with ", exception)
            }
    }

    fun bindDataTripDetails(tripDeailsDAO: TripDeailsDAO){
        tripNameDetailsTextView.text = tripDeailsDAO.tripNameDetail
        tripDetailsLocationTextView.text = tripDeailsDAO.triplocation
        tripRateTextView.text = tripDeailsDAO.triprating
        tripPriceTextView.text = tripDeailsDAO.tripPrice
        tripDayTextView.text = tripDeailsDAO.tripDay
        descriptionTextView.text = tripDeailsDAO.tripDesc
        scheduleTextView.text = tripDeailsDAO.tripSchedule
        durationTextView.text = tripDeailsDAO.duration
        partyTypeTextView.text = tripDeailsDAO.tripPartyType
        includeTextView.text = tripDeailsDAO.tripInclude
        remarkTextView.text = tripDeailsDAO.tripRemark
    }
}
