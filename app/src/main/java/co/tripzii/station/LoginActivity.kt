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

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Login"
        login_button.setOnClickListener {
            performLogin()

        }
        forgot_textView.setOnClickListener {
            Log.d(TAG, "Try to show Forgot my password activity")

            //เปิดหน้า forgot password
            val intent = Intent(this, SetupCompanyProfileActivity::class.java)
            startActivity(intent)
        }
    }
    private fun performLogin() {
        val email = username_textInput.text.toString()
        val password = password_textInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "email is :" + email)
        Log.d(TAG, "Password : $password")

        //เชื่อมต่อ firebase auth

//        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                if (!it.isSuccessful) return@addOnCompleteListener
//
//                // else if successful
//                Log.d(TAG, "Successfully created user with uid: ${it.result?.user?.uid}")
//
//
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "Failed to create user: ${it.message}")
//                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT)
//                    .show()
//            }
    }
}
