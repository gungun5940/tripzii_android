package co.tripzii.station

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.adapter.ReportCommissionAdapter
import co.tripzii.station.model.ReportCommissionDAO
import kotlinx.android.synthetic.main.activity_report_commission.*

class ReportCommissionActivity : AppCompatActivity() {

    lateinit var adapter: ReportCommissionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_commission)
        supportActionBar?.title = "Report commission"

        val mount = resources.getStringArray(R.array.month_arrays)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            mount
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        monthSpinner.adapter = adapter
        monthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        val report = ArrayList<ReportCommissionDAO>()
        report.add(ReportCommissionDAO("Chaing mai", "19/02/2020", "3,500", "500"))
        report.add(ReportCommissionDAO("Chaing rai", "12/02/2020", "4,500", "600"))
        report.add(ReportCommissionDAO("Phuket", "13/02/2020", "5,500", "700"))
        report.add(ReportCommissionDAO("Bangkok", "14/02/2020", "6,500", "800"))

        val reportAdapter = ReportCommissionAdapter(report)
        commissionRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commissionRecyclerView.adapter = reportAdapter
    }
}
