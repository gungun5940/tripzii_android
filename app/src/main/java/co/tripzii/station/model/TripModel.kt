package co.tripzii.station.model

data class TripModel (
    var tripId : String = "",
    var nametrip: String = "",
    var price: String = "",
    var rate: String = "",
    val image: MutableList<Image> = mutableListOf()
)
data class Image(
    var id : String? = null,
    var url : String? = null
)