package co.tripzii.station.ui.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TicketViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ticket Fragment"
    }
    val text: LiveData<String> = _text
}