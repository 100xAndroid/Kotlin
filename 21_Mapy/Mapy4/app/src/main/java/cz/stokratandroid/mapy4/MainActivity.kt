package cz.stokratandroid.mapy4

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.CustomCap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

override fun onMapReady(map: GoogleMap) {

    // nastavit pozici mapy a jeji zvetseni
    val souradnice = LatLng(49.9453, 14.1871)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(souradnice, 13f))

    // trasa
    val trasaVlastnosti = PolylineOptions()
    trasaVlastnosti.geodesic(true) // zohlednit zakřivení Zeme
    trasaVlastnosti.color(Color.GRAY) // barva spojnice
    trasaVlastnosti.add(LatLng(49.9323, 14.1753)) // souradnice jednotlivych bodu
    trasaVlastnosti.add(LatLng(49.9328, 14.1799))
    trasaVlastnosti.add(LatLng(49.9345, 14.1806))
    trasaVlastnosti.add(LatLng(49.9340, 14.1830))
    trasaVlastnosti.add(LatLng(49.9369, 14.1846))
    trasaVlastnosti.add(LatLng(49.9383, 14.1871))
    trasaVlastnosti.add(LatLng(49.9465, 14.1834))
    trasaVlastnosti.add(LatLng(49.9511, 14.1925))
    trasaVlastnosti.add(LatLng(49.9534, 14.2045))
    trasaVlastnosti.add(LatLng(49.9583, 14.2067))
    trasaVlastnosti.add(LatLng(49.9611, 14.2036))

    // startovni znacka
    trasaVlastnosti.startCap(CustomCap(BitmapDescriptorFactory.fromResource(R.drawable.start)))
    // zaobleni konce trasy
    trasaVlastnosti.endCap(RoundCap())
    map.addPolyline(trasaVlastnosti)

    // cilova znacka
val souradniceCil = LatLng(49.9611, 14.2036)
val vlastnostiZnacky = MarkerOptions().position(souradniceCil)
vlastnostiZnacky.icon(BitmapDescriptorFactory.fromResource(R.drawable.cil))
map.addMarker(vlastnostiZnacky)

    /*
    // polygon
    val polygonVlastnosti = PolygonOptions()
    polygonVlastnosti.add(LatLng(49.9415, 14.1919)) // souradnice jednotlivych bodu
    polygonVlastnosti.add(LatLng(49.9411, 14.1873))
    polygonVlastnosti.add(LatLng(49.9390, 14.1870))
    polygonVlastnosti.add(LatLng(49.9375, 14.1883))
    polygonVlastnosti.add(LatLng(49.9385, 14.1918))
    polygonVlastnosti.strokeColor(Color.DKGRAY) // barva ohraniceni
    polygonVlastnosti.fillColor(0x40808080) // barva vyplne (808080) a pruhlednost (40)
    val polygon = map.addPolygon(polygonVlastnosti)
    */

    /*
    // kruh
    val souradniceKruhu = LatLng(49.9392, 14.1879)
    val circleOptions = CircleOptions()
    circleOptions.center(souradniceKruhu) // stred kruhu
    circleOptions.radius(1200.0) // polomer
    circleOptions.strokeColor(Color.BLACK) // barva ohraniceni
    circleOptions.fillColor(0x40808080) // barva vyplne (808080) a pruhlednost (40)
    circleOptions.strokeWidth(5f) // sila cary
    map.addCircle(circleOptions)
    */
}
}

