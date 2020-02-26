package co.tripzii.station.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.TicketModel
import co.tripzii.station.model.TransferModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.transfer_item.view.*
import kotlinx.android.synthetic.main.trip_ticket_item.view.*
import kotlinx.android.synthetic.main.trip_ticket_item.view.priceTicketTextView
import kotlinx.android.synthetic.main.trip_ticket_item.view.rateTicketTextView


class TransferAdapter(
    private var dataTransfer : List<TransferModel>,
    private var onSelectItem:(transfer: TransferModel) -> Unit

) : RecyclerView.Adapter<TransferAdapter.TransferHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferHolder {
        return TransferHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transfer_item, parent, false)
        )
    }
    override fun getItemCount() = dataTransfer.size

    override fun onBindViewHolder(holder:TransferHolder, position: Int) {
        holder.bind(dataTransfer[position])
        holder.itemView.setOnClickListener{
            onSelectItem.invoke(dataTransfer[position])
        }
    }

    class TransferHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: TransferModel) {
            itemView.apply {
                nameTransferTextView.text = report.nametransfer
                rateTransferTextView.text = report.rate
                priceTransferTextView.text = report.price
                Log.d("image", report.toString())
                if (report.image?.size != 0) {
                    Picasso.get().load(report.image?.first()?.url).into(transferImageView)
                }
            }
        }
    }
}