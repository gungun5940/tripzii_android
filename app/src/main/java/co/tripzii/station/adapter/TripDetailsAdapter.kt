package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TripDeailsDAO
import co.tripzii.station.model.TripModel
import kotlinx.android.synthetic.main.activity_trip_details.view.*
import kotlinx.android.synthetic.main.timeline.view.*

class TripDetailsAdapter ( val dataTripDetails : List<TripModel>) : RecyclerView.Adapter<TripDetailsAdapter.TripDetailsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripDetailsViewHolder {
        return TripDetailsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.timeline, parent, false)
        )
    }
    override fun getItemCount() = dataTripDetails.size

    override fun onBindViewHolder(holder: TripDetailsViewHolder, position: Int) {
        holder.bind(dataTripDetails[position])
        holder.itemView.setOnClickListener{
        }

    }
    class TripDetailsViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TripModel) {
            itemView.apply {
                tripNameDetailsTextView.text = report.nametrip
            }
        }

        class Trip
    }
}