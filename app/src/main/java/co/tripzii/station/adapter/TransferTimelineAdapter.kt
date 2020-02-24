package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TransferTimelineDAO
import kotlinx.android.synthetic.main.transfer_timeline.view.*

class TransferTimelineAdapter( private val dataTransferTimeline: List<TransferTimelineDAO>)
    : RecyclerView.Adapter<TransferTimelineAdapter.TransferTimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferTimelineViewHolder {
        return TransferTimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transfer_timeline, parent, false)
        )
    }
    override fun getItemCount() = dataTransferTimeline.size

    override fun onBindViewHolder(holder: TransferTimelineViewHolder, position: Int) {
        holder.bind(dataTransferTimeline[position])
        holder.itemView.setOnClickListener {
        }
    }
    class TransferTimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TransferTimelineDAO) {
            itemView.apply {
                transferTimeTextView.text = report.time
                transferTimeLineDetailTextView.text = report.detail
            }
        }
    }
}