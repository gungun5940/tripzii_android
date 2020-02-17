package co.tripzii.station

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        supportActionBar?.title = "Edit Profile"
        val province = resources.getStringArray(R.array.province)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, province)
        editSelectProvinceAutoCompleteTextView.threshold = 0
        editSelectProvinceAutoCompleteTextView.setAdapter(adapter)
        editSelectProvinceAutoCompleteTextView.setOnClickListener {
            editSelectProvinceAutoCompleteTextView.showDropDown()
        }
        editSelectProvinceAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            hideKeyboard()
            editSelectProvinceAutoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext, "Selected : $selectedItem", Toast.LENGTH_SHORT).show()
        }
        editSelectProvinceAutoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext, "Suggestion closed.", Toast.LENGTH_SHORT).show()
        }
        editSelectProvinceAutoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener {
                view, b -> if (b) { editSelectProvinceAutoCompleteTextView.showDropDown() }
        }
        editProileButton.setOnClickListener {
            progressBar.show(this, "Please waiting..")
            Handler().postDelayed({}, 2000)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
