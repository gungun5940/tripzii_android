package co.tripzii.station

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    private var auth = FirebaseAuth.getInstance()
    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        supportActionBar?.title = "Reset my password"
        resetButton.setOnClickListener {
            changePassword()
            progressBar.show(this, "Please Wait...")
            Handler().postDelayed({}, 2000)
        }
    }

    private fun changePassword() {
        if (currentPasswordTextInput.text!!.isNotEmpty() &&
            newPasswordTextInput.text!!.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, currentPasswordTextInput.text.toString())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                                user.updatePassword(newPasswordTextInput.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(
                                                this,
                                                "Password changed successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            auth.signOut()
                                            startActivity(Intent(this, LoginActivity::class.java))
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                    }
                        }
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
        } else {
            Toast.makeText(this, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
        }
    }
}
