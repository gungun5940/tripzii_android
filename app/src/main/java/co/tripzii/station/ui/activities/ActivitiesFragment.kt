package co.tripzii.station.ui.activities

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
import co.tripzii.station.adapter.SliderImageAdapter
import co.tripzii.station.adapter.TripAdapter
import co.tripzii.station.model.ImageModel
import co.tripzii.station.model.TripModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.view_pager.*
import java.util.*
import kotlin.collections.ArrayList


class ActivitiesFragment : Fragment() {

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(
        R.drawable.img_doiinthanon, R.drawable.img_mist,
        R.drawable.img_sea, R.drawable.img_temple, R.drawable.img_phiphi_island)

    private val db = FirebaseFirestore.getInstance()

    companion object {

        private var viewPage: ViewPager? = null

        private var currentPage = 0

        private var numPage = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_activities, container, false)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()
        viewPage = view.findViewById(R.id.viewPager)
        viewPage!!.adapter = SliderImageAdapter(activity!!, imageModelArrayList!!)
        val indicator = view.findViewById(R.id.indicator) as CirclePageIndicator
        indicator.setViewPager(viewPage)
        val density = resources.displayMetrics.density
        indicator.setRadius(4 * density)
        numPage = imageModelArrayList!!.size
        val handler = Handler() // Auto start of viewpager
        val update = Runnable {
            if (currentPage == numPage) {
                currentPage = 0
            }
            viewPage!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3500, 3500)
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener { // Pager listener over indicator

            override fun onPageSelected(position: Int) {
                currentPage = position
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
                    Log.d("trip", trip.toString())
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                })
                recyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recyclerView.adapter = tripAdapter
                tripAdapter.notifyDataSetChanged()
                val tripPopularAdapter = TripAdapter(trip, onSelectItem = { trip ->
                    Log.d("PopularTrip", trip.toString())
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                })
                popularRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                popularRecyclerView.adapter = tripPopularAdapter
                tripPopularAdapter.notifyDataSetChanged()
                val tripRecommendedAdapter = TripAdapter(trip, onSelectItem = { trip ->
                    Log.d("RecommendedTrip", trip.toString())
                    val intent = Intent(activity, TripDetailsActivity::class.java)
                    intent.putExtra("trip", trip)
                    startActivity(intent)
                })
                recommendedRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recommendedRecyclerView.adapter = tripRecommendedAdapter
                tripRecommendedAdapter.notifyDataSetChanged()
            }
    }
}
