package co.tripzii.station

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.title = "Edit Profile"
        var province = resources.getStringArray(R.array.province)
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,province)
        edit_select_province_autoCompleteTextView.threshold=0
        edit_select_province_autoCompleteTextView.setAdapter(adapter)
        edit_select_province_autoCompleteTextView.setOnClickListener {
            edit_select_province_autoCompleteTextView.showDropDown()
        }
        edit_select_province_autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            hideKeyboard()
            edit_select_province_autoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem", Toast.LENGTH_SHORT).show()
        }
        edit_select_province_autoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.", Toast.LENGTH_SHORT).show()
        }
        edit_select_province_autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b -> if(b){edit_select_province_autoCompleteTextView.showDropDown()}
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