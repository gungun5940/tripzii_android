package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ViewFlipper

class ImageDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        val viewFlipper = findViewById<ViewFlipper>(R.id.tripDetailsImageView)
        if (viewFlipper != null) {
            viewFlipper.setInAnimation(this, R.anim.slide_in_rigth)
            viewFlipper.setOutAnimation(this, R.anim.slide_out_left)
        }
    }
}
