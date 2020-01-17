package co.tripzii.station.ui.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.tripzii.station.R

class TicketFragment : Fragment() {

    private lateinit var ticketViewModel: TicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ticketViewModel =
            ViewModelProviders.of(this).get(TicketViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ticket, container, false)
        val textView: TextView = root.findViewById(R.id.text_ticket)
        ticketViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}