package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_interesting_trip.*

class InterestingTripActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interesting_trip)

        val storage = FirebaseStorage.getInstance()
// Create a reference to a file from a Google Cloud Storage URI
        val gsReference = storage.getReferenceFromUrl("img")

    }
}
