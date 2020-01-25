package co.tripzii.station

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import co.tripzii.station.adapter.ReportCommissionAdapter
import co.tripzii.station.adapter.TimelineAdapter
import co.tripzii.station.model.ReportCommissionDAO
import co.tripzii.station.model.TimelineDAO
import kotlinx.android.synthetic.main.activity_report_commission.*
import kotlinx.android.synthetic.main.activity_trip_details.*

class TripDetailsActivity : AppCompatActivity() {

    lateinit var adapter: TimelineAdapter
    lateinit var action: View.OnClickListener

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)
        supportActionBar?.title = "Back homepage"

        val report = ArrayList<TimelineDAO>()
        report.add(TimelineDAO("7:00  am", "Pick up at the hotel"))
        report.add(TimelineDAO("11:00 am", "Visit Wachira Than and Siritharn waterfalls"))
        report.add(TimelineDAO("12:00 pm", "Explore the highest point in Thailand, 2,565 meters above sea level"))
        report.add(TimelineDAO("1:00  pm", "Visit Nabhamethanidol and Nabhapolbhumisiri (The Great Holy Relics Pagoda Nabhamethanidol and Nabhapolbhumisiri)"))
        report.add(TimelineDAO("3:00  pm", "Visiting the Hmong market and Hmong hill tribe village"))
        report.add(TimelineDAO("4:00  pm", "Depart from Doi Inthanon National Park toward Chiang Mai"))
        report.add(TimelineDAO("5:00  pm", "Drop off at your hotel in downtown Chiang Mai"))


        val reportAdapter = TimelineAdapter(report)
        timelineRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        timelineRecyclerView.adapter = reportAdapter

    }
}
