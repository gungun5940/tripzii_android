package co.tripzii.station

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_setup_company_profile.*
import java.util.*

class BookingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        supportActionBar?.title = "Booking"
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        select_day_autoCompleteTextView.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener
            { datePicker, i, i2, i3 ->
            select_day_autoCompleteTextView.setText("" + i+ "/" + i2 + "/" + i3) },year,month,day)
            datePickerDialog.show()
        }
        var packages = resources.getStringArray(R.array.packages)
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,packages)
        select_package_autoCompleteTextView.threshold=0
        select_package_autoCompleteTextView.setAdapter(adapter)
        select_package_autoCompleteTextView.setOnClickListener {
            select_package_autoCompleteTextView.showDropDown() }
        select_package_autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            hideKeyboard()
            select_package_autoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        select_package_autoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.",Toast.LENGTH_SHORT).show()
        }
        select_package_autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b -> if(b){select_package_autoCompleteTextView.showDropDown()}
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
}

