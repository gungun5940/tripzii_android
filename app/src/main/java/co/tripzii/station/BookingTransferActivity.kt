package co.tripzii.station

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.WriterException
import kotlinx.android.synthetic.main.activity_booking_transfer.*
import java.util.*

class BookingTransferActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var numberAdults = 0
    var numberChild = 0

    private var checkOutTransferButton: Button? = null

    private var pickupLocationTransferTextInput: TextInputEditText? = null

    private val progressBar = ProgressBarActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_transfer)
            supportActionBar?.title = "Booking"
            pickupLocationTransferTextInput = findViewById(R.id.pickupLocationTransferTextInput) as TextInputEditText
            checkOutTransferButton = findViewById(R.id.checkOutTransferButton) as Button
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            selectDateTransferAutoCompleteTextView.setOnClickListener {
                val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener
                { datePicker, i, i2, i3 ->
                    selectDateTransferAutoCompleteTextView.setText("" + i + "/" + i2 + "/" + i3) }, year, month, day)
                datePickerDialog.show()
            }
            val packages = resources.getStringArray(R.array.packages)
            val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, packages)
            selectPackageTransferAutoCompleteTextView.threshold = 0
            selectPackageTransferAutoCompleteTextView.setAdapter(adapter)
            selectPackageTransferAutoCompleteTextView.setOnClickListener {
                selectPackageTransferAutoCompleteTextView.showDropDown() }
            selectPackageTransferAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener {
                    parent, view, position, id ->
                hideKeyboard()
                selectPackageTransferAutoCompleteTextView.dismissDropDown()
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Selected : $selectedItem", Toast.LENGTH_SHORT).show()
            }
            selectPackageTransferAutoCompleteTextView.setOnDismissListener {
                Toast.makeText(applicationContext, "Suggestion closed.", Toast.LENGTH_SHORT).show()
            }
            selectPackageTransferAutoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener {
                    view, b -> if (b) { selectPackageTransferAutoCompleteTextView.showDropDown() }
            }
            val nationality = resources.getStringArray(R.array.nationality)
            val nationalityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nationality)
            nationalityTransferAutoCompleteTextView.threshold = 0
            nationalityTransferAutoCompleteTextView.setAdapter(nationalityAdapter)
            nationalityTransferAutoCompleteTextView.setOnClickListener {
                nationalityTransferAutoCompleteTextView.showDropDown() }
            nationalityTransferAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener {
                    parent, view, position, id ->
                hideKeyboard()
                nationalityTransferAutoCompleteTextView.dismissDropDown()
                val selectedItem = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, "Selected : $selectedItem", Toast.LENGTH_SHORT).show()
            }
            nationalityTransferAutoCompleteTextView.setOnDismissListener {
                Toast.makeText(applicationContext, "Suggestion closed.", Toast.LENGTH_SHORT).show()
            }
            nationalityTransferAutoCompleteTextView.onFocusChangeListener = View.OnFocusChangeListener {
                    view, b -> if (b) { nationalityTransferAutoCompleteTextView.showDropDown() }
            }
            val plus = findViewById<Button>(R.id.increaseAdultsTransferButton)
            val minus = findViewById<Button>(R.id.decreaseAdultsTransferButton)

            plus.setOnClickListener {
                increaseInteger(plus)
            }
            minus.setOnClickListener {
                decreaseInteger(minus)
            }
            checkOutTransferButton!!.setOnClickListener {
                if (pickupLocationTransferTextInput!!.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please fill in information! ", Toast.LENGTH_SHORT).show()
                } else {
                    try {
                        val intent = Intent(this, PaymentTransferActivity::class.java)
                        intent.putExtra("pickupLocationTransfer", "" + pickupLocationTransferTextInput!!.getText().toString())
                        startActivity(intent)
                    } catch (e: WriterException) {
                        e.printStackTrace()
                    }
                }
                progressBar.show(this, "Checking out...")
                Handler().postDelayed({
                    progressBar.dialog.dismiss()
                }, 2000)
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Please select") // To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            TODO("Select item") // To change body of created functions use File | Settings | File Templates.
        }

        private fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        private fun Context.hideKeyboard(view: View) {
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
            val displayInteger = findViewById<View>(R.id.numberOfAdultsTransferTextView) as TextView
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
            val displayInteger = findViewById<View>(R.id.numberOfChildTransferTextView) as TextView
            displayInteger.text = "" + number
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            R.id.menu_refresh
            val intent = Intent(this, BookingTransferActivity::class.java)
            startActivity(intent)
            return super.onOptionsItemSelected(item)
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.nav_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }
}
