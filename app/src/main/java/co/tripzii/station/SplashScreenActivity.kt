package co.tripzii.station

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val background = object : Thread(){
            override fun run() {
                try {
                    Thread.sleep(2000)
                    if (user != null){
                        val intent = Intent(baseContext, AllTripActivity::class.java)
                        startActivity(intent)
                    }else{
                        val intent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
