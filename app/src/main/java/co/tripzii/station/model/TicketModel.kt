package co.tripzii.station.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TicketModel (
    var ticketId : String? = null,
    var nameticket: String? = null,
    var price: String? = null,
    var rate: String? = null,
    val image: MutableList<ImageTicket>? = mutableListOf(),
    var category: String? = null,
    var countDate: String? = null,
    var description: String? = null,
    var durationSchedule: String? = null,
    var duration: String? = null,
    var durationType:String? = null,
    var include: String? = null,
    var remark: String? = null,
    var longitude: String? = null,
    var latitude: String? = null,
    var serviceFood: String? = null,
    var servicePickup: String? = null,
    var serviceGuide: String? = null,
    var serviceAccident: String? = null,
    var province: String? = null,
    var ticketTimeline : List<TicketTimelineDAO>? = null
) : Parcelable

@Parcelize
data class ImageTicket(
    var id : String? = null,
    var url : String? = null
) : Parcelable