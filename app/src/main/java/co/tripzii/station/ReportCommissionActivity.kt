package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ReportCommissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_commission)
        supportActionBar?.title = "Report commission"
    }
}
