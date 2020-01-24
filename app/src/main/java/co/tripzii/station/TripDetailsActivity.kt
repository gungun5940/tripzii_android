package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TripDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)
        supportActionBar?.title = "Back homepage"

    }
}
