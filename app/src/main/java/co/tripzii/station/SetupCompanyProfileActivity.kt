package co.tripzii.station

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_setup_company_profile.*

class SetupCompanyProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_company_profile)
        supportActionBar?.title = "Setup Company Profile"
        var province = resources.getStringArray(R.array.province)
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,province)
        selectProvinceAutoCompleteTextView.threshold=0
        selectProvinceAutoCompleteTextView.setAdapter(adapter)
        selectProvinceAutoCompleteTextView.setOnClickListener {
            selectProvinceAutoCompleteTextView.showDropDown() }
        selectProvinceAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            hideKeyboard()
            selectProvinceAutoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        selectProvinceAutoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.",Toast.LENGTH_SHORT).show()
        }
        selectProvinceAutoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b -> if(b){selectProvinceAutoCompleteTextView.showDropDown()}
        }
        setupButton.setOnClickListener {
            progressBar.show(this,"Saving...")
            Handler().postDelayed({}, 2000)
            val intent = Intent(this, AllTripziiActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        val intent = Intent(this,HotelAccountActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
