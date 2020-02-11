package co.tripzii.station.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import co.tripzii.station.R
import kotlinx.android.synthetic.main.fragment_ticket.*
import java.util.Observer

class TicketFragment : Fragment() {

//    private lateinit var ticketViewModel: TicketViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        ticketViewModel = ViewModelProvider(this).get(TicketViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_ticket, container, false)
//        val textView: TextView = root.findViewById(R.id.text_ticket)
//        ticketViewModel.text.observe(this, androidx.lifecycle.Observer  {
//            textView.text = it
//        })
//        return root
//    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_ticket, container, false)

    companion object {
        fun newInstance(): TicketFragment = TicketFragment()
    }
}