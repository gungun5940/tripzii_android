package co.tripzii.station.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripModel(
    var tripId: String? = null,
    var nametrip: String? = null,
    var price: String? = null,
    var rate: String? = null,
    val image: MutableList<ImageTrip>? = mutableListOf(),
    var category: String? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var countDate: String? = null,
    var description: String? = null,
    var durationSchedule: String? = null,
    var duration: String? = null,
    var durationType: String? = null,
    var include: String? = null,
    var remark: String? = null,
    var serviceFood: String? = null,
    var serviceGuide: String? = null,
    var serviceAccident: String? = null,
    var servicePickup: String? = null,
    var province: String? = null,
    var timeline: List<TimelineDAO>? = null
) : Parcelable

@Parcelize
data class ImageTrip(
    var id: String? = null,
    var url: String? = null
) : Parcelable


