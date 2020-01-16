package co.tripzii.station

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.nav_menu.*

class AllTripActivity : AppCompatActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_trip)
        setHumburgerButton()
        homeMenuTextView.setOnClickListener {
            val intent = Intent(this, AllTripActivity::class.java)
            startActivity(intent)
        }
        moneyMenuTextView.setOnClickListener {
            val intent = Intent(this, CurrencyPopupActivity::class.java)
            startActivity(intent)
        }
        translateMenuTextView.setOnClickListener {
            val intent = Intent(this, ChangeLanguagePopupActivity::class.java)
            startActivity(intent)
        }
        adminMenuTextView.setOnClickListener {
            val intent = Intent(this, HotelAccountActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setHumburgerButton() {
        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this
            ,drawerLayout
            ,R.string.navigation_drawer_open
            ,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle.syncState()
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}
