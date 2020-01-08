package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog


class FilterTripPopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_trip)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sorted by")
        val tripFilter = resources.getStringArray(R.array.filter_trip)
        val checkedItem = 1 // cow

        builder.setSingleChoiceItems(tripFilter, checkedItem) { dialog, which ->

        }
        builder.setPositiveButton("OK") { dialog, which ->
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }
}
