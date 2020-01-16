package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class CurrencyPopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_popup)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Currency")
        val currency = resources.getStringArray(R.array.currency_arrays)
        val checkedItem = 1 // cow
        builder.setSingleChoiceItems(currency, checkedItem) { dialog, which ->AllTripActivity()}
        builder.setPositiveButton("OK") { dialog, which ->AllTripActivity()}
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }
}
