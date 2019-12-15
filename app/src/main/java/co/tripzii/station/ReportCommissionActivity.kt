package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import co.tripzii.station.adapter.ReportCommissionAdapter
import co.tripzii.station.model.ReportCommissionDAO
import com.google.android.gms.common.api.Response
import kotlinx.android.synthetic.main.activity_report_commission.*


class ReportCommissionActivity : AppCompatActivity() {

    lateinit var adapter: ReportCommissionAdapter
    lateinit var action: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_commission)
        supportActionBar?.title = "Report commission"


        val mount = resources.getStringArray(R.array.month_arrays)

        // Initializing an ArrayAdapter
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            mount // Array
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter;

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

            }

            override fun onNothingSelected(parent: AdapterView<*>){
            }


        }


    }

//    private fun fetchMovies(){
//        refreshLayout.isRefreshing = true


        private fun showComission(Commission: List<ReportCommissionDAO>) {
        recyclerViewCommission.layoutManager = LinearLayoutManager(this)
        recyclerViewCommission.adapter = ReportCommissionAdapter(Commission)
    }
}
