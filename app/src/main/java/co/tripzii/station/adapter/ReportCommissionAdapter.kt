package co.tripzii.station.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.tripzii.station.R
import co.tripzii.station.model.ReportCommissionDAO
import kotlinx.android.synthetic.main.list_commission.view.*

class ReportCommissionAdapter(val dataCommision: List<ReportCommissionDAO>) : RecyclerView.Adapter<ReportCommissionAdapter.CommissionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommissionViewHolder {
        return CommissionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_commission, parent, false)
        )
    }
    override fun getItemCount() = dataCommision.size

    override fun onBindViewHolder(holder: CommissionViewHolder, position: Int) {
        holder.bind(dataCommision[position])
        holder.itemView.setOnClickListener {
        }
    }

    class CommissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(report: ReportCommissionDAO) {
            itemView.apply {
                tripNameCommissionTextView.text = report.name
                tripDateCommissionTextView.text = report.date
                tripPriceCommissionTextView.text = report.price
                tripTotalCommissionTextView.text = report.total
            }
        }
    }
}
