package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TimelineDAO
import kotlinx.android.synthetic.main.timeline.view.*

class TimelineAdapter(val dataTimeline: List<TimelineDAO>) : RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        return TimelineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.timeline, parent, false)
        )
    }
    override fun getItemCount() = dataTimeline.size

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(dataTimeline[position])
        holder.itemView.setOnClickListener {
        }
    }
    class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TimelineDAO) {
            itemView.apply {
                timeTextView.text = report.time
                timeLineDetailTextView.text = report.detail
            }
        }
    }
}