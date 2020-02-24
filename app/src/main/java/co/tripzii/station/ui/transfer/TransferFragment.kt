package co.tripzii.station.ui.transfer

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
import co.tripzii.station.TransferDetailsActivity
import co.tripzii.station.TripDetailsActivity
import co.tripzii.station.adapter.SliderImageTicketAdapter
import co.tripzii.station.adapter.TransferAdapter
import co.tripzii.station.adapter.TripTicketAdapter
import co.tripzii.station.model.ImageModel
import co.tripzii.station.model.TicketModel
import co.tripzii.station.model.TransferModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.view_pager_ticket.*
import kotlinx.android.synthetic.main.view_pager_transfer.*
import java.util.*
import kotlin.collections.ArrayList

class TransferFragment : Fragment() {

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.img_transfer_bangkok, R.drawable.img_suvarnabhumi,
        R.drawable.img_taxi_transfer,R.drawable.img_transfer,R.drawable.img_airport)

    private val db = FirebaseFirestore.getInstance()

    companion object {

        private var viewPageTransfer: ViewPager? = null

        private var currentPageTransfer = 0

        private var numPageTransfer = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transfer, container, false)
        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()
        viewPageTransfer = view.findViewById(R.id.viewPagerTransfer)
        viewPageTransfer!!.adapter = SliderImageTicketAdapter(activity!!, imageModelArrayList!!)
        val indicator = view.findViewById(R.id.indicatorTransfer) as CirclePageIndicator
        indicator.setViewPager(viewPageTransfer)
        val density = resources.displayMetrics.density
        indicator.setRadius(4 * density)
        numPageTransfer = imageModelArrayList!!.size
        val handler = Handler() // Auto start of viewpager
        val update = Runnable {
            if (currentPageTransfer == numPageTransfer) {
                currentPageTransfer = 0
            }
            viewPageTransfer!!.setCurrentItem(currentPageTransfer++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3500, 3500)
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener { // Pager listener over indicator

            override fun onPageSelected(position: Int) {
                currentPageTransfer = position
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
        val transfer : MutableList<TransferModel> = mutableListOf()
        val getDataTransfer = db.collection("transfer service")
        getDataTransfer.get()
            .addOnCompleteListener { task ->
                for (ds in task.result?.documentChanges!!) {
                    val item = ds.document.toObject(TransferModel::class.java).apply {
                        transferId = ds.document.id
                    }
                    when(ds.type) {
                        DocumentChange.Type.ADDED -> {
                            val items = transfer.filter { it.transferId == item.transferId }
                            if (items.isEmpty()) item.let { transfer.add(it) }
                        }
                        DocumentChange.Type.MODIFIED -> {
                            val indexTransfer = transfer.indexOfFirst { it.transferId == item.transferId }
                            item.apply { transfer[indexTransfer] = this }
                        }
                        else ->{}
                    }
                }
                val transferAdapter = TransferAdapter(transfer, onSelectItem = { transfer ->
                    val intent = Intent(activity, TransferDetailsActivity::class.java)
                    intent.putExtra("transfer", transfer)
                    startActivity(intent)
                })
                recyclerViewTransfer.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recyclerViewTransfer.adapter = transferAdapter
                transferAdapter.notifyDataSetChanged()
                val transferPopularAdapter = TransferAdapter(transfer, onSelectItem = {transfer ->
                    Log.d("PopularTransfer", transfer.toString())
                    val intent = Intent(activity, TransferDetailsActivity::class.java)
                    intent.putExtra("transfer", transfer)
                    startActivity(intent)
                })
                popularTransferRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                popularTransferRecyclerView.adapter = transferPopularAdapter
                transferPopularAdapter.notifyDataSetChanged()
                val recommendedTransferAdapter = TransferAdapter(transfer, onSelectItem = {transfer ->
                    val intent = Intent(activity, TransferDetailsActivity::class.java)
                    intent.putExtra("transfer", transfer)
                    startActivity(intent)
                })
                recommendedTransferRecyclerView.layoutManager = LinearLayoutManager(activity,
                    RecyclerView.HORIZONTAL, false)
                recommendedTransferRecyclerView.adapter = recommendedTransferAdapter
                recommendedTransferAdapter.notifyDataSetChanged()
            }
    }
}
