package co.tripzii.station.ui.ticket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.tripzii.station.AllTripziiActivity
import co.tripzii.station.HotelAccountActivity
import co.tripzii.station.R
import co.tripzii.station.TripDetailsActivity
import co.tripzii.station.adapter.SliderImageAdapter
import co.tripzii.station.adapter.TripTicketAdapter
import co.tripzii.station.model.ImageModel
import co.tripzii.station.model.TicketModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.fragment_ticket.*
import kotlinx.android.synthetic.main.nav_menu.*
import kotlinx.android.synthetic.main.view_pager.*
import kotlinx.android.synthetic.main.view_pager_ticket.*
import java.util.*
import kotlin.collections.ArrayList

class TicketFragment : Fragment() {
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    lateinit var drawerLayout: DrawerLayout
    private var imageModelArrayList: ArrayList<ImageModel>? = null
    lateinit var inflater: LayoutInflater
//    val container: ViewGroup? = null
    private val myImageList = intArrayOf(R.drawable.img_art, R.drawable.img_chiangmaizoo,
        R.drawable.img_temple,R.drawable.img_thesea,R.drawable.img_fish)
    private val db = FirebaseFirestore.getInstance()

    companion object {
        private var viewPageTicket: ViewPager? = null
        private var currentPageTicket = 0
        private var NumPageTicket = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_ticket, container, false)
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        inflater.inflate(R.layout.fragment_ticket, drawerLayoutTicket, true)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()
        init()
        setHumburgerButton()
        homeMenuTextView.setOnClickListener {
            val intent = Intent(activity, AllTripziiActivity::class.java)
            startActivity(intent)
        }
        moneyMenuTextView.setOnClickListener {
            val builder = AlertDialog.Builder(AllTripziiActivity())
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
            val builder = AlertDialog.Builder(AllTripziiActivity())
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
            val intent = Intent(activity, HotelAccountActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setHumburgerButton() {
        drawerLayout = view!!.findViewById(R.id.drawerLayoutTicket)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            activity
            , drawerLayout
            , R.string.navigation_drawer_open
            , R.string.navigation_drawer_close
        )
        actionBarDrawerToggle?.apply { drawerLayout.addDrawerListener(this) }
        imgMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        imgFilter.setOnClickListener { val builder = AlertDialog.Builder(AllTripziiActivity())
            builder.setTitle("Sorted by")
            val tripFilter = resources.getStringArray(R.array.filter_trip)
            val checkedItem = 1 // cow
            builder.setSingleChoiceItems(tripFilter, checkedItem) { _, _ -> }
            builder.setPositiveButton("OK") { _, _ -> }
            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show() }
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
        viewPageTicket = view?.findViewById(R.id.viewPagerTicket)
        viewPageTicket!!.adapter = SliderImageAdapter(activity!!.applicationContext, imageModelArrayList!!)
        val indicatorTicket = view?.findViewById(R.id.indicatorTicket) as CirclePageIndicator
        indicatorTicket.setViewPager(viewPagerTicket)
        val density = resources.displayMetrics.density
        indicatorTicket.setRadius(4 * density)
        NumPageTicket = imageModelArrayList!!.size
        val handlerTicket = Handler() // Auto start of viewpager
        val updateTicket = Runnable {
            if (currentPageTicket == NumPageTicket) {
                currentPageTicket = 0
            }
            viewPageTicket!!.setCurrentItem(currentPageTicket++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handlerTicket.post(updateTicket)
            }
        }, 3000, 3000)
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener { // Pager listener over indicator

            override fun onPageSelected(position: Int) {
                currentPageTicket = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val tripticket : MutableList<TicketModel> = mutableListOf()
        val getDataTicket = db.collection("attraction ticket")
        getDataTicket.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TicketModel::class.java).apply {
                        ticketId = ds.document.id
                    }
                    when(ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = tripticket.filter { it.ticketId == item.ticketId }
                            if (items.isEmpty()) item.let { tripticket.add(it) }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val indexTicket = tripticket.indexOfFirst { it.ticketId == item.ticketId }
                            item.apply { tripticket[indexTicket] = this }
                        }
                        else ->{}
                    }
                }
                val tripTicketAdapter = TripTicketAdapter(tripticket, onSelectItem = { tripticket ->
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", tripticket)
                    startActivity(intent)
                })
                recyclerViewTicket.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recyclerViewTicket.adapter = tripTicketAdapter
                tripTicketAdapter.notifyDataSetChanged()
                val tripTicketPopularAdapter = TripTicketAdapter(tripticket, onSelectItem = {tripticket ->
                    Log.d("PopularTrip", tripticket.toString())
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", tripticket)
                    startActivity(intent)
                })
                popularTicketRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                popularTicketRecyclerView.adapter = tripTicketPopularAdapter
                tripTicketPopularAdapter.notifyDataSetChanged()
                val tripRecommendedTicketAdapter = TripTicketAdapter(tripticket, onSelectItem = {tripticket ->
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", tripticket)
                    startActivity(intent)
                })
                recommendedTicketRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recommendedTicketRecyclerView.adapter = tripRecommendedTicketAdapter
                tripRecommendedTicketAdapter.notifyDataSetChanged()
            }
    }
}
