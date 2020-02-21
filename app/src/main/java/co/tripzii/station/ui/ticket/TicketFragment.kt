package co.tripzii.station.ui.ticket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import co.tripzii.station.R
import co.tripzii.station.TripDetailsActivity
import co.tripzii.station.adapter.SliderImageTicketAdapter
import co.tripzii.station.adapter.TripTicketAdapter
import co.tripzii.station.model.ImageModel
import co.tripzii.station.model.TicketModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.view_pager_ticket.*
import java.util.*
import kotlin.collections.ArrayList

class TicketFragment : Fragment(){

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.img_art, R.drawable.img_chiangmaizoo,
        R.drawable.img_draemworld,R.drawable.img_thesea,R.drawable.img_fish)

    private val db = FirebaseFirestore.getInstance()

    companion object {

        private var viewPageTicket: ViewPager? = null

        private var currentPageTicket = 0

        private var numPageTicket = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ticket, container, false)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()
        viewPageTicket = view.findViewById(R.id.viewPagerTicket)
        viewPageTicket!!.adapter = SliderImageTicketAdapter(activity!!, imageModelArrayList!!)
        val indicator = view.findViewById(R.id.indicatorTicket) as CirclePageIndicator
        indicator.setViewPager(viewPageTicket)
        val density = resources.displayMetrics.density
        indicator.setRadius(4 * density)
        numPageTicket = imageModelArrayList!!.size
        val handler = Handler() // Auto start of viewpager
        val update = Runnable {
            if (currentPageTicket == numPageTicket) {
                currentPageTicket = 0
            }
            viewPageTicket!!.setCurrentItem(currentPageTicket++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3500, 3500)
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener { // Pager listener over indicator

            override fun onPageSelected(position: Int) {
                currentPageTicket = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {
            }
        })
        return view
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

    override fun onStart() {
        super.onStart()
        val tripTicket : MutableList<TicketModel> = mutableListOf()
        val getDataTicket = db.collection("attraction ticket")
        getDataTicket.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TicketModel::class.java).apply {
                        ticketId = ds.document.id
                    }
                    when(ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = tripTicket.filter { it.ticketId == item.ticketId }
                            if (items.isEmpty()) item.let { tripTicket.add(it) }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val indexTicket = tripTicket.indexOfFirst { it.ticketId == item.ticketId }
                            item.apply { tripTicket[indexTicket] = this }
                        }
                        else ->{}
                    }
                }
                val tripTicketAdapter = TripTicketAdapter(tripTicket, onSelectItem = { tripTicket ->
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("ticket", tripTicket)
                    startActivity(intent)
                })
                recyclerViewTicket.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recyclerViewTicket.adapter = tripTicketAdapter
                tripTicketAdapter.notifyDataSetChanged()
                val tripTicketPopularAdapter = TripTicketAdapter(tripTicket, onSelectItem = {tripTicket ->
                    Log.d("PopularTrip", tripTicket.toString())
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("ticket", tripTicket)
                    startActivity(intent)
                })
                popularTicketRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                popularTicketRecyclerView.adapter = tripTicketPopularAdapter
                tripTicketPopularAdapter.notifyDataSetChanged()
                val tripRecommendedTicketAdapter = TripTicketAdapter(tripTicket, onSelectItem = {tripTicket ->
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("ticket", tripTicket)
                    startActivity(intent)
                })
                recommendedTicketRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recommendedTicketRecyclerView.adapter = tripRecommendedTicketAdapter
                tripRecommendedTicketAdapter.notifyDataSetChanged()
            }
    }
}
