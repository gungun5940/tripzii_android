package co.tripzii.station

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_booking.*
import java.util.*

class BookingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var numberAdults = 0
    var numberChild = 0
    private var checkOutButton: Button? = null
    private var pickupLocationTextInput: TextInputEditText? = null
    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        supportActionBar?.title = "Booking"
        pickupLocationTextInput = findViewById(R.id.pickupLocationTextInput) as TextInputEditText
        checkOutButton = findViewById(R.id.checkOutButton) as Button
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        selectDateAutoCompleteTextView.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener
            { datePicker, i, i2, i3 ->
            selectDateAutoCompleteTextView.setText("" + i+ "/" + i2 + "/" + i3) },year,month,day)
            datePickerDialog.show()
        }
        var packages = resources.getStringArray(R.array.packages)
        var adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,packages)
        selectPackageAutoCompleteTextView.threshold=0
        selectPackageAutoCompleteTextView.setAdapter(adapter)
        selectPackageAutoCompleteTextView.setOnClickListener {
            selectPackageAutoCompleteTextView.showDropDown() }
        selectPackageAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            hideKeyboard()
            selectPackageAutoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        selectPackageAutoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.",Toast.LENGTH_SHORT).show()
        }
        selectPackageAutoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b -> if(b){selectPackageAutoCompleteTextView.showDropDown()}
        }
        var nationality = resources.getStringArray(R.array.nationality)
        var nationailtyadapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,nationality)
        nationalityAutoCompleteTextView.threshold=0
        nationalityAutoCompleteTextView.setAdapter(nationailtyadapter)
        nationalityAutoCompleteTextView.setOnClickListener {
            nationalityAutoCompleteTextView.showDropDown() }
        nationalityAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            hideKeyboard()
            nationalityAutoCompleteTextView.dismissDropDown()
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        nationalityAutoCompleteTextView.setOnDismissListener {
            Toast.makeText(applicationContext,"Suggestion closed.",Toast.LENGTH_SHORT).show()
        }
        nationalityAutoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener{
                view, b -> if(b){nationalityAutoCompleteTextView.showDropDown()}
        }
        val plus = findViewById<Button>(R.id.increaseAdultsButton)
        val minus = findViewById<Button>(R.id.decreaseAdultsButton)

        plus.setOnClickListener {
            increaseInteger(plus)
        }
        minus.setOnClickListener {
            decreaseInteger(minus)
        }
        checkOutButton!!.setOnClickListener {
            if (pickupLocationTextInput!!.text.toString().trim { it <= ' ' }.length == 0) {
                Toast.makeText(this, "Enter String!", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val intent = Intent(this, PaymentActivity::class.java)
                    intent.putExtra("pickupLocation",""+ pickupLocationTextInput!!.getText().toString())
                    startActivity(intent)

                } catch (e: WriterException) {
                    e.printStackTrace()
                }
            }
            progressBar.show(this,"Checking out...")
            Handler().postDelayed({}, 2000)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Please select") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Select item") //To change body of created functions use File | Settings | File Templates.
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun increaseInteger(view: View?) {
        numberAdults = numberAdults + 1
        displayNumberOfAdults(numberAdults)
    }

    fun decreaseInteger(view: View?) {
        numberAdults = numberAdults - 1
        displayNumberOfAdults(numberAdults)
    }

    private fun displayNumberOfAdults(number: Int) {
        val displayInteger = findViewById<View>(R.id.numberOfAdultsTextView) as TextView
        displayInteger.text = "" + number
    }

    fun increaseIntegerChild(view: View?) {
        numberChild = numberChild + 1
        displayNumberOfChild(numberChild)
    }

    fun decreaseIntegerChild(view: View?) {
        numberChild = numberChild - 1
        displayNumberOfChild(numberChild)
    }

    private fun displayNumberOfChild(number: Int) {
        val displayInteger = findViewById<View>(R.id.numberOfChildTextView) as TextView
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


