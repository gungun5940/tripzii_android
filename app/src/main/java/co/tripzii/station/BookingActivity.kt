package co.tripzii.station

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_booking.*
import java.util.*


class BookingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var minteger = 0
    var mintegerchild = 0

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
        var nationality = resources.getStringArray(R.array.nationality)
        var nationailtyadapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,nationality)
        nationality_autoCompleteTextView.threshold=0
        nationality_autoCompleteTextView.setAdapter(nationailtyadapter)
        nationality_autoCompleteTextView.setOnClickListener {
            nationality_autoCompleteTextView.showDropDown() }
        nationality_autoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            hideKeyboard()
            nationality_autoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        nationality_autoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.",Toast.LENGTH_SHORT).show()
        }
        nationality_autoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b -> if(b){nationality_autoCompleteTextView.showDropDown()}
        }
        val plus = findViewById<Button>(R.id.increase_adults)
        val minus = findViewById<Button>(R.id.decrease_adults)

        plus.setOnClickListener {

            increaseInteger(plus)
        }



        minus.setOnClickListener {

            decreaseInteger(minus)
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

    fun increaseInteger(view: View?) {
        minteger = minteger + 1
        display(minteger)
    }

    fun decreaseInteger(view: View?) {
        minteger = minteger - 1
        display(minteger)
    }

    private fun display(number: Int) {
        val displayInteger = findViewById<View>(R.id.number_adults_textView) as TextView
        displayInteger.text = "" + number
    }
    fun increaseIntegerChild(view: View?) {
        mintegerchild = mintegerchild + 1
        displayChild(mintegerchild)
    }

    fun decreaseIntegerChild(view: View?) {
        mintegerchild = mintegerchild - 1
        displayChild(mintegerchild)
    }

    private fun displayChild(number: Int) {
        val displayInteger = findViewById<View>(R.id.number_child_textView) as TextView
        displayInteger.text = "" + number
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            R.id.menu_refresh
                val intent = Intent(this, BookingActivity::class.java)
                startActivity(intent)
            return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}


