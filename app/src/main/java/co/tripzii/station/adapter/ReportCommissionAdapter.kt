package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.ReportCommissionDAO
import kotlinx.android.synthetic.main.list_commission.view.*

class ReportCommissionAdapter ( val dataCommision : List<ReportCommissionDAO>) : RecyclerView.Adapter<ReportCommissionAdapter.CommissionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommissionViewHolder {
        return CommissionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_commission, parent, false)
        )
    }
    override fun getItemCount() = dataCommision.size


    override fun onBindViewHolder(holder: CommissionViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            //do sth in here
        }

        holder.itemView.trip_name.text = this.dataCommision[position].name
        holder.itemView.trip_date.text = this.dataCommision[position].date
        holder.itemView.trip_price.text = this.dataCommision[position].price
        holder.itemView.trip_total_com_price.text = this.dataCommision[position].total

    }

    class CommissionViewHolder( view: View) : RecyclerView.ViewHolder(view)
}

