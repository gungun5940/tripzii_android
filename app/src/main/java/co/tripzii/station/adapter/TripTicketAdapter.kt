package co.tripzii.station.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TicketModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.trip_ticket_item.view.*


class TripTicketAdapter(
    private var dataTripTicket : List<TicketModel>,
    private var onSelectItem:(ticket: TicketModel) -> Unit

) : RecyclerView.Adapter<TripTicketAdapter.TicketHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        return TicketHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.trip_ticket_item, parent, false)
        )
    }
    override fun getItemCount() = dataTripTicket.size

    override fun onBindViewHolder(holder:TicketHolder, position: Int) {
        holder.bind(dataTripTicket[position])
        holder.itemView.setOnClickListener{
            onSelectItem.invoke(dataTripTicket[position])
        }
    }

    class TicketHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TicketModel) {
            itemView.apply {
                nameTicketTextView.text = report.nameticket
                rateTicketTextView.text = report.rate
                priceTicketTextView.text = report.price
                Log.d("image", report.toString())
                if (report.image?.size != 0) {
                    Picasso.get().load(report.image?.first()?.url).into(tripTicketImageView)
                }
            }
        }
    }
}