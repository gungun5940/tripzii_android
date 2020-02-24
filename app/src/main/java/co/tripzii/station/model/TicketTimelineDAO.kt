package co.tripzii.station.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketTimelineDAO (
    var time: String? = null,
    var detail: String? = null
) : Parcelable
