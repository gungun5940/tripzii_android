package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class ChangeLanguagePopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language_popup)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Language")
        val language = resources.getStringArray(R.array.language_arrys)
        val checkedItem = 1 // cow
        builder.setSingleChoiceItems(language, checkedItem) { dialog, which -> }
        builder.setPositiveButton("OK") { dialog, which -> }
        builder.setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.show()
    }
}
