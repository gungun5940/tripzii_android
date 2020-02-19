package co.tripzii.station

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.tripzii.station.adapter.SliderImageAdapter
import co.tripzii.station.adapter.TripAdapter
import co.tripzii.station.model.ImageModel
import co.tripzii.station.model.TripModel
import co.tripzii.station.ui.activities.ActivitiesFragment
import co.tripzii.station.ui.ticket.TicketFragment
import co.tripzii.station.ui.transfer.TransferFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.viewpagerindicator.CirclePageIndicator
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_all_trip.*
import kotlinx.android.synthetic.main.hamberger_bar.*
import kotlinx.android.synthetic.main.nav_menu.*
import kotlinx.android.synthetic.main.view_pager.*

class AllTripActivity : AppCompatActivity() {

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_trip)
        setHamburgerButton()
        homeMenuTextView.setOnClickListener {
            val intent = Intent(this, AllTripActivity::class.java)
            startActivity(intent)
        }
        moneyMenuTextView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Currency")
            val currency = resources.getStringArray(R.array.currency_arrays)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(currency, checkedItem) { _, _ -> }
            builder.setPositiveButton("OK") { _, _ -> }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }
        translateMenuTextView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Language")
            val language = resources.getStringArray(R.array.language_arrys)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(language, checkedItem) { _, _ -> }
            builder.setPositiveButton("OK") { _, _ -> }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()
        }
        adminMenuTextView.setOnClickListener {
            val intent = Intent(this, HotelAccountActivity::class.java)
            startActivity(intent)
        }
        val navView: BottomNavigationView = findViewById(R.id.navBottomNavigation)
        val navController = findNavController(R.id.navFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                    R.id.navigation_activities,R.id.navigation_ticket,R.id.navigation_transfer
            )
        )
        appBarConfiguration.fallbackOnNavigateUpListener
        navView.setupWithNavController(navController)
    }

    private fun setHamburgerButton() {
        drawerLayout = findViewById(R.id.drawerLayout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        actionBarDrawerToggle?.apply { drawerLayout.addDrawerListener(this) }
        imgMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        imgFilter.setOnClickListener { val builder = AlertDialog.Builder(this)
            builder.setTitle("Sorted by")
            val tripFilter = resources.getStringArray(R.array.filter_trip)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(tripFilter, checkedItem) { _, _ -> }
            builder.setPositiveButton("OK") { _, _ -> }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show() }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
