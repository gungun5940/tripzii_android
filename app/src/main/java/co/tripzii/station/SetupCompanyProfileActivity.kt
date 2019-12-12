package co.tripzii.station

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_setup_company_profile.*

class SetupCompanyProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_company_profile)
        supportActionBar?.title = "Setup Company Profile"
        var province = resources.getStringArray(R.array.province)
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,province)
        select_province_autoCompleteTextView.threshold=0
        select_province_autoCompleteTextView.setAdapter(adapter)
        select_province_autoCompleteTextView.setOnFocusChangeListener(
            { view, b -> if (b) select_province_autoCompleteTextView.showDropDown()})

    }
}
