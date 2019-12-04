package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        supportActionBar?.title = "Reset my password"
    }
}
