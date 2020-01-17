package co.tripzii.station.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransferViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is transfer service Fragment"
    }
    val text: LiveData<String> = _text
}