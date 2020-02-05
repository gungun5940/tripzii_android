package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TripModel
import kotlinx.android.synthetic.main.trip_item.view.*

class TripAdapter( private var dataTrip : List<TripModel>) : RecyclerView.Adapter<TripAdapter.TripHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripHolder {
        return TripHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trip_item, parent, false)
        )
    }
    override fun getItemCount() = dataTrip.size

    override fun onBindViewHolder(holder:TripAdapter.TripHolder, position: Int) {
        holder.bind(dataTrip[position])
        holder.itemView.setOnClickListener{
        }

    }

    class TripHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TripModel) {
            itemView.apply {
                nameTripTextView.text = report.nametrip
                rateTextView.text = report.rate
                priceTripTextView.text = report.price
            }
        }
    }
}