package co.tripzii.station

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_hotel_account.*

class HotelAccountActivity : AppCompatActivity() {

    companion object{
        val TAG = "HotelAccountActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_account)
        supportActionBar?.title = "Hotel Account"
        edit_profile_textView.setOnClickListener {
            Log.d(TAG, "Try to show edit profile activity")
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        change_password_textView.setOnClickListener {
            Log.d(TAG, "Try to show reset my password activity")
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        commission_textView.setOnClickListener {
            Log.d(TAG, "Try to show report commission activity")
            val intent = Intent(this, ReportCommissionActivity::class.java)
            startActivity(intent)
        }
    }
}
