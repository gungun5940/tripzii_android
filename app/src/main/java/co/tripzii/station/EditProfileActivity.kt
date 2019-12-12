package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.title = "Edit Profile"
        var province = resources.getStringArray(R.array.province)
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,province)
        edit_select_province_autoCompleteTextView.threshold=0
        edit_select_province_autoCompleteTextView.setAdapter(adapter)
        edit_select_province_autoCompleteTextView.setOnFocusChangeListener(
            { view, b -> if (b) edit_select_province_autoCompleteTextView.showDropDown()})
    }
}
