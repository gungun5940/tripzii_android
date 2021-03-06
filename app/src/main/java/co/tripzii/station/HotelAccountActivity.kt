package co.tripzii.station

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hotel_account.*

class HotelAccountActivity : AppCompatActivity() {

    companion object {
        var TAG = "HotelAccountActivity"
    }

    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_account)
        supportActionBar?.title = "Hotel Account"
        editProfileTextView.setOnClickListener {
            Log.d(TAG, "Try to show edit profile activity")
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
        changePasswordTextView.setOnClickListener {
            Log.d(TAG, "Try to show reset my password activity")
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
        commissionTextView.setOnClickListener {
            Log.d(TAG, "Try to show report commission activity")
            val intent = Intent(this, ReportCommissionActivity::class.java)
            startActivity(intent)
        }
        logoutButton.setOnClickListener {
            progressBar.show(this, "Logging out...")
            Handler().postDelayed({}, 2000)
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
