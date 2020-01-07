package co.tripzii.station

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object{
        val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Login"
        loginButton.setOnClickListener {
            performLogin()
        }
        forgotPasswordTextView.setOnClickListener {
            Log.d(TAG, "Try to show forgot my password activity")
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent) //เปิดหน้า forgot password
        }
        loginButton.setOnClickListener {
            val intent = Intent(this, SetupCompanyProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin() {
        val email = usernameTextInput.text.toString()
        val password = passwordTextInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d(TAG, "Email :" + email)
        Log.d(TAG, "Password : $password")
    }
}
