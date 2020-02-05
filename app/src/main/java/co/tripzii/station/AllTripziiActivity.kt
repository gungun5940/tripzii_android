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
import kotlinx.android.synthetic.main.activity_all_tripzii.*
import kotlinx.android.synthetic.main.hamberger_bar.*
import kotlinx.android.synthetic.main.nav_menu.*
import kotlinx.android.synthetic.main.view_pager.*
import java.util.*
import kotlin.collections.ArrayList


class AllTripziiActivity : AppCompatActivity() {

    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    lateinit var drawerLayout: DrawerLayout
    private var imageModelArrayList: ArrayList<ImageModel>? = null
    private val myImageList = intArrayOf(R.drawable.img_doiinthanon, R.drawable.img_mist,
        R.drawable.img_sea,R.drawable.img_temple,R.drawable.img_phiphi_island)
//    lateinit var recyclerViews: RecyclerView

    private val db = FirebaseFirestore.getInstance()
//    private val tripRef = db.collection("alltrip")
//        .document("01")
//        .collection("Category")
//        .get()
//        .addOnSuccessListener { result ->
//        for (document in result) {
//            println("${document.id} => ${document.data}")
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.d(this.toString(), "Error getting documents: ", exception)
//        }

//    private var adapter: TripAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tripzii)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()
        init()

        val trip : MutableList<TripModel> = mutableListOf()
        val getData = db.collection("alltrip")

        getData.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TripModel::class.java).apply {
                        tripId = ds.document.id
                    }

                    when(ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = trip.filter { it.tripId == item.tripId }
                            if (items.isEmpty()) item.let { trip.add(it) }
                        }

                        DocumentChange.Type.MODIFIED -> {
                            val index = trip.indexOfFirst { it.tripId == item.tripId }
                            item.apply { trip[index] = this }
                        }

                        else ->{}
                    }

                    Log.d("tripZ", trip.toString())
                    val allTrip = TripAdapter(trip)
                    recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
                    recyclerView.adapter = allTrip
                }
            }
        if(trip.size != 0) {
            Log.d("tripZZZZ", trip.toString())
        }


//        initRecycler()
        setHumburgerButton()
//        setUpRecyclerView()
        homeMenuTextView.setOnClickListener {
            val intent = Intent(this, AllTripziiActivity::class.java)
            startActivity(intent)
        }
        moneyMenuTextView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Currency")
            val currency = resources.getStringArray(R.array.currency_arrays)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(currency, checkedItem) { _, _ -> }
            builder.setPositiveButton("OK") { _, _ ->}
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
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_ticket -> {
                    replaceFragment(TicketFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_transfer -> {
                    replaceFragment(TransferFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
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

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container , fragment)
        fragmentTransaction.commit()
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
        mPager = findViewById(R.id.viewPager)
        mPager!!.adapter = SliderImageAdapter(this, imageModelArrayList!!)
        val indicator = findViewById(R.id.indicator) as CirclePageIndicator
        indicator.setViewPager(viewPager)
        val density = resources.displayMetrics.density
        indicator.setRadius(4 * density)
        NUM_PAGES = imageModelArrayList!!.size
        val handler = Handler() // Auto start of viewpager
        val update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2000, 2000)
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

//    private fun setUpRecyclerView() {
//        val tripRef = db.collection("alltrip")
//        val query: Query = tripRef.orderBy("price",Query.Direction.DESCENDING)
//        val options: FirestoreRecyclerOptions<Trip> = FirestoreRecyclerOptions.Builder<Trip>()
//            .setQuery(query, Trip::class.java)
//            .build()
//        adapter = TripAdapter(options)
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(this@AllTripziiActivity,
//            RecyclerView.HORIZONTAL, false)
//        recyclerView.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        adapter!!.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter!!.stopListening()
//    }

    companion object {
        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }

//    private fun initRecycler(){
//        recyclerViews = recyclerView
//
//        recyclerViews.apply {
//            layoutManager = LinearLayoutManager(this@AllTripziiActivity,
//                RecyclerView.VERTICAL, false)
//            adapter = ParentAdapter(Trip)
//        }
//    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }
}

