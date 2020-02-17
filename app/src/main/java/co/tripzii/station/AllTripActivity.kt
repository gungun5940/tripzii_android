package co.tripzii.station

import android.app.PendingIntent.getActivity
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

    private val progressBar = ProgressBarActivity()

    lateinit var drawerLayout: DrawerLayout

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.img_doiinthanon, R.drawable.img_mist,
        R.drawable.img_sea, R.drawable.img_temple, R.drawable.img_phiphi_island)

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_trip)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()
        init()
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
        navBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_activities -> {
                    replaceFragment(ActivitiesFragment())
//                    val activitiesFragment = ActivitiesFragment.newInstance()
//                    replaceFragment(activitiesFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_ticket -> {
                    val ticketFragment = TicketFragment.newInstance()
                    replaceFragment(ticketFragment)
                    setHamburgerButton()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_transfer -> {
                    val transferFragment = TransferFragment.newInstance()
                    replaceFragment(transferFragment)
                    setHamburgerButton()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
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

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun populateList(): ArrayList<ImageModel> {
        val list = ArrayList<ImageModel>()
        for (i in 0..4) {
            val imageModel = ImageModel()
            imageModel.setImageDrawables(myImageList[i])
            list.add(imageModel)
        }
        return list
    }

    private fun init() {
        viewPage = findViewById(R.id.viewPager)
        viewPage!!.adapter = SliderImageAdapter(this, imageModelArrayList!!)
        val indicator = findViewById(R.id.indicator) as CirclePageIndicator
        indicator.setViewPager(viewPager)
        val density = resources.displayMetrics.density
        indicator.setRadius(4 * density)
        NumPage = imageModelArrayList!!.size
        val handler = Handler() // Auto start of viewpager
        val update = Runnable {
            if (currentPage == NumPage) {
                currentPage = 0
            }
            viewPage!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener { // Pager listener over indicator

            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {
            }
        })
    }

    companion object {
        private var viewPage: ViewPager? = null
        private var currentPage = 0
        private var NumPage = 0
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        val trip: MutableList<TripModel> = mutableListOf()
        val getData = db.collection("alltrip")
        getData.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TripModel::class.java).apply {
                        tripId = ds.document.id
                    }
                    when (ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = trip.filter { it.tripId == item.tripId }
                            if (items.isEmpty()) item.let { trip.add(it) }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val index = trip.indexOfFirst { it.tripId == item.tripId }
                            item.apply { trip[index] = this }
                        }
                        else -> {}
                    }
                }
                val tripAdapter = TripAdapter(trip, onSelectItem = { trip ->
                    Log.d("alltrip", trip.toString())
                    val intent = Intent(this@AllTripActivity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                    progressBar.show(this, "Checking out...")
                    Handler().postDelayed({}, 2000)

                })
                recyclerView.layoutManager = LinearLayoutManager(this,
                    RecyclerView.HORIZONTAL, false)
                recyclerView.adapter = tripAdapter
                tripAdapter.notifyDataSetChanged()
                val tripPopularAdapter = TripAdapter(trip, onSelectItem = { trip ->
                    Log.d("PopularTrip", trip.toString())
                    val intent = Intent(this@AllTripActivity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                    progressBar.show(this, "Checking out...")
                    Handler().postDelayed({}, 2000)
                })
                popularRecyclerView.layoutManager = LinearLayoutManager(this,
                    RecyclerView.HORIZONTAL, false)
                popularRecyclerView.adapter = tripPopularAdapter
                tripPopularAdapter.notifyDataSetChanged()
                val tripRecommendedAdapter = TripAdapter(trip, onSelectItem = { trip ->
                    Log.d("RecommendedTrip", trip.toString())
                    val intent = Intent(this@AllTripActivity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                    progressBar.show(this, "Checking out...")
                    Handler().postDelayed({}, 2000)
                })
                recommendedRecyclerView.layoutManager = LinearLayoutManager(this,
                    RecyclerView.HORIZONTAL, false)
                recommendedRecyclerView.adapter = tripRecommendedAdapter
                tripRecommendedAdapter.notifyDataSetChanged()
            }
    }
}
