package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TicketTimelineDAO
import kotlinx.android.synthetic.main.ticket_timeline.view.*

class TicketTimelineAdapter ( private val dataTicketTimeline: List<TicketTimelineDAO>)
    : RecyclerView.Adapter<TicketTimelineAdapter.TicketTimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketTimelineViewHolder {
        return TicketTimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transfer_timeline, parent, false)
        )
    }
    override fun getItemCount() = dataTicketTimeline.size

    override fun onBindViewHolder(holder: TicketTimelineViewHolder, position: Int) {
        holder.bind(dataTicketTimeline[position])
        holder.itemView.setOnClickListener {
        }
    }
    class TicketTimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TicketTimelineDAO) {
            itemView.apply {
                ticketTimeTextView.text = report.time
                ticketTimeLineDetailTextView.text = report.detail
            }
        }
    }
}