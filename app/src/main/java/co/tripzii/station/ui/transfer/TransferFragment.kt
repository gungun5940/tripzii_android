package co.tripzii.station.ui.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.tripzii.station.R

class TransferFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
    inflater.inflate(R.layout.fragment_transfer, container, false)

    companion object {
        fun newInstance(): TransferFragment = TransferFragment()
    }
}
