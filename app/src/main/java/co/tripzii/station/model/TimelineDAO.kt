package co.tripzii.station.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimelineDAO (
    var time: String? = null,
    var detail: String? = null
) : Parcelable