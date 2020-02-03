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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_trip_details.*

class TripDetailsActivity : AppCompatActivity() {

    lateinit var adapter: TimelineAdapter
//    lateinit var adapter2: TripDetailsAdapter
    lateinit var action: View.OnClickListener
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        val tripNameDetails = findViewById(R.id.tripNameDetailsTextView) as TextView
        val tripRate = findViewById(R.id.tripRateTextView) as TextView
        val tripPrice = findViewById(R.id.tripPriceTextView) as TextView



        db.collection("trip")
            .document("01")
            .collection("type")
            .document("001")
            .collection("trips")
            .addSnapshotListener { querySnapshot, error ->
                val document = querySnapshot?.documentChanges
                for (doc in document!!)  {
                    Log.d("DocumentSnapshot", doc.toString())
                }
            }


//        val docRef = db.collection("trip").document("1").collection("Lampang")
//        docRef.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("1", "c data: ${document}")
//
////                    tripNameDetails.text = document.getString("title")
////                    tripRate.text = document.getString("score")
////                    tripPrice.text = document.getString("price")
//
//                } else {
//                    Log.d("2", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("3", "get failed with ", exception)
//            }


        val reportComs = ArrayList<TimelineDAO>()
        reportComs.add(TimelineDAO("7:00  am", "Pick up at the hotel"))
        reportComs.add(TimelineDAO("11:00 am", "Visit Wachira Than and Siritharn waterfalls"))
        reportComs.add(TimelineDAO("12:00 pm", "Explore the highest point in Thailand, 2,565 meters above sea level"))
        reportComs.add(TimelineDAO("1:00  pm", "Visit Nabhamethanidol and Nabhapolbhumisiri (The Great Holy Relics Pagoda Nabhamethanidol and Nabhapolbhumisiri)"))
        reportComs.add(TimelineDAO("3:00  pm", "Visiting the Hmong market and Hmong hill tribe village"))
        reportComs.add(TimelineDAO("4:00  pm", "Depart from Doi Inthanon National Park toward Chiang Mai"))
        reportComs.add(TimelineDAO("5:00  pm", "Drop off at your hotel in downtown Chiang Mai"))
        val reportAdapter = TimelineAdapter(reportComs)
        timelineRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        timelineRecyclerView.adapter = reportAdapter
//        val reportTripDetails = TripDeailsDAO(
//            "Doi intanon ",
//            "Chaing mai ",
//            "4.2",
//            "5000",
//            "3",
//            "Wachira Tarn Waterfall Siritarn Waterfall Doi Inthanon – The highest peak in Thailand King and Queen’s PagodasHmong marketKarenhilltribe village",
//            "dairy trip",
//            "full day",
//            "group",
//            "trip incllude",
//            "trip remark")
//        tripNameDetailsTextView.text = reportTripDetails.tripNameDetail
//        tripDetailsLocationTextView.text = reportTripDetails.triplocation
//        tripRateTextView.text = reportTripDetails.triprating
//        tripPriceTextView.text = reportTripDetails.tripprice
//        tripDayTextView.text = reportTripDetails.tripDay
//        descDetailsTextView.text = reportTripDetails.tripDesc
//        scheduleTextView.text = reportTripDetails.tripSchedule
//        durationTextView.text = reportTripDetails.duration
//        typeTextView.text = reportTripDetails.tripType
//        includeTextView.text = reportTripDetails.tripInclude
//        remarkTextView.text = reportTripDetails.tripRemark

    }
}
