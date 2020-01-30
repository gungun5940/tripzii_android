package co.tripzii.station

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import co.tripzii.station.adapter.ReportCommissionAdapter
import co.tripzii.station.adapter.TimelineAdapter
//import co.tripzii.station.adapter.TripDetailsAdapter
import co.tripzii.station.model.ReportCommissionDAO
import co.tripzii.station.model.TimelineDAO
import co.tripzii.station.model.TripDeailsDAO
import kotlinx.android.synthetic.main.activity_report_commission.*
import kotlinx.android.synthetic.main.activity_trip_details.*

class TripDetailsActivity : AppCompatActivity() {

    lateinit var adapter: TimelineAdapter
//    lateinit var adapter2: TripDetailsAdapter
    lateinit var action: View.OnClickListener

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

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
        val reportTripDetails = TripDeailsDAO(
            "Doi intanon ",
            "Chaing mai ",
            "4.2",
            "5000",
            "3",
            "Wachira Tarn Waterfall Siritarn Waterfall Doi Inthanon – The highest peak in Thailand King and Queen’s PagodasHmong marketKarenhilltribe village",
            "dairy trip",
            "full day",
            "group",
            "trip incllude",
            "trip remark")
        tripNameDetailsTextView.text = reportTripDetails.tripNameDetail
        tripDetailsLocationTextView.text = reportTripDetails.triplocation
        tripRateTextView.text = reportTripDetails.triprating
        tripPriceTextView.text = reportTripDetails.tripprice
        tripDayTextView.text = reportTripDetails.tripDay
        descDetailsTextView.text = reportTripDetails.tripDesc
        scheduleTextView.text = reportTripDetails.tripSchedule
        durationTextView.text = reportTripDetails.duration
        typeTextView.text = reportTripDetails.tripType
        includeTextView.text = reportTripDetails.tripInclude
        remarkTextView.text = reportTripDetails.tripRemark

    }
}
