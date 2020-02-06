package co.tripzii.station.model

class TripDeailsDAO (
    var tripNameDetail  : String? = null ,
    var triprating  : String? = null ,
    var tripPrice  : String? = null ,
    var tripDay: String? = null ,
    var tripDesc  : String? = null ,
    var tripSchedule: String? = null ,
    var duration: String? = null ,
    var tripPartyType:String? = null ,
    var tripInclude: String? = null ,
    var tripRemark: String? = null ,
    var tripLocation: String? = null,
    var timeline : List<TimelineDAO>? = null

)
