package co.tripzii.station

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class BasicMapDemoActivity :
    AppCompatActivity(),
    OnMapReadyCallback {

    val LocationPin = LatLng(-33.862, 151.21)
    val ZOOM_LEVEL = 13f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        val mapFragment : SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.locationMapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(LocationPin, ZOOM_LEVEL))
            addMarker(MarkerOptions().position(LocationPin))
        }
    }
}