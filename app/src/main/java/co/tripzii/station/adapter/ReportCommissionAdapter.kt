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
        holder.bind(dataCommision[position])
        holder.itemView.setOnClickListener{
            //do sth in here
        }

    }

    class CommissionViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: ReportCommissionDAO) {
            itemView.apply {
                trip_name.text = report.name
                trip_date.text = report.name
                trip_price.text = report.name
                trip_total_com_price.text = report.name
            }
        }
    }
}

