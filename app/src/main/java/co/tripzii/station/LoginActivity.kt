package co.tripzii.station

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        var TAG = "LoginActivity"
    }
    private lateinit var auth: FirebaseAuth
    private var sharedPref: SharedPreferences? = null
    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.title = "Login"
        loginButton.setOnClickListener {
            performLogin()
            progressBar.show(this, " logging in...")
            Handler().postDelayed({
                progressBar.dialog.dismiss()
            }, 2000)
        }
        forgotPasswordTextView.setOnClickListener {
            progressBar.show(this, "Please Wait...")
            Handler().postDelayed({
                progressBar.dialog.dismiss()
            }, 2000)
            Log.d(TAG, "Try to show forgot my password activity")
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent) // เปิดหน้า forgot password
        }
        auth = FirebaseAuth.getInstance()
            AuthStateListener { firebaseAuth ->
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val userId = firebaseUser.uid
                    val userEmail = firebaseUser.email
                    sharedPref = getPreferences(Context.MODE_PRIVATE)
                    val editor = sharedPref!!.edit()
                    editor.putString("firebasekey", userId)
                    editor.apply()
                }
            }
    }

    private fun performLogin() {
        val email = usernameTextInput.text.toString()
        val password = passwordTextInput.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("login", "Login with email/pw: $email/***")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (password.length < 6) {
                        passwordTextInput.error = "Please check your password"
                        Log.w(TAG, "Enter password less than 6 characters")
                    } else {
                        Toast.makeText(this, "Authentication Failed!", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "Authentication Failed:" + task.exception)
                    }
                } else {
                    Toast.makeText(this, "Sing in successfully!", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Sing in successfully!")
                    val intent = Intent(this, SetupCompanyProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
    }
}
