package co.tripzii.station

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import kotlinx.android.synthetic.main.activity_all_tripzii.*
import kotlinx.android.synthetic.main.hamberger_bar.imgMenu
import kotlinx.android.synthetic.main.nav_menu.*

class AllTripziiActivity : AppCompatActivity() {

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tripzii)
        val navView: BottomNavigationView = findViewById(R.id.navBottomNavigation)
        val navController = findNavController(R.id.navFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_activities,R.id.navigation_ticket,R.id.navigation_transfer
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setHumburgerButton()
        homeMenuTextView.setOnClickListener {
            val intent = Intent(this, AllTripziiActivity::class.java)
            startActivity(intent)
        }
        moneyMenuTextView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Currency")
            val currency = resources.getStringArray(R.array.currency_arrays)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(currency, checkedItem) { dialog, which ->}
            builder.setPositiveButton("OK") { dialog, which ->}
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }
        translateMenuTextView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Language")
            val language = resources.getStringArray(R.array.language_arrys)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(language, checkedItem) { dialog, which -> }
            builder.setPositiveButton("OK") { dialog, which -> }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }
        adminMenuTextView.setOnClickListener {
            val intent = Intent(this, HotelAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setHumburgerButton() {
        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this
            , drawerLayout
            , R.string.navigation_drawer_open
            , R.string.navigation_drawer_close
        )
        actionBarDrawerToggle?.apply { drawerLayout.addDrawerListener(this) }
        imgMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }
}

