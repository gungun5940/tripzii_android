package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HotelAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_account)
        supportActionBar?.title = "Hotel Account"
    }
}
