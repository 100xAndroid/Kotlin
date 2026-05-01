package cz.stokratandroid.mapy3

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

// pro udalost kliknuti primo na znacku je nutne navic pouzit take interface OnMarkerClickListener
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

        // pripravime si souradnice ktere chceme zobrazit
        val souradnice = LatLng(49.939, 14.188)

        // nastavit vlastnosti znacky
        val vlastnostiZnacky = MarkerOptions().position(souradnice)
        vlastnostiZnacky.icon(BitmapDescriptorFactory.fromResource(R.drawable.znacka))
        val znacka1 = map.addMarker(vlastnostiZnacky)
        znacka1!!.tag = "znacka1"
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            // definice kompletniho okna (nevyuzijeme)
            override fun getInfoWindow(znacka: Marker): View? {
                return null
            }

            // definice obsahu informacniho okna
            override fun getInfoContents(znacka: Marker): View? {
                // ukazatel na formular marker_info
                val v = layoutInflater.inflate(R.layout.marker_info, null)

                // ziskat reference na objekty ve formulari
                val txtNadpis = v.findViewById<View>(R.id.txtNadpis) as TextView
                val imgObrazek = v.findViewById<View>(R.id.imgObrazek) as ImageView
                val txtPopis = v.findViewById<View>(R.id.txtPopis) as TextView
                if (znacka.tag === "znacka1") {
                    txtNadpis.text = "Hrad Karlštejn"
                    imgObrazek.setImageResource(R.drawable.karlstejn)
                    txtPopis.text =
                        "Středověký hrad, založený králem Karlem IV. v roce 1348. První část hradu byla dokončena roku 1357."
                }
                return v
            }
        })

        // udalost kliknuti do informacniho okna
        map.setOnInfoWindowClickListener { znacka ->
            if (znacka.tag === "znacka1") {
                Toast.makeText(this@MainActivity, "Informace o hradu Karlštejn", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // kdyby bylo potreba reagovat na kliknuti primo na znacku, nastavit listener pro kliknuti
        // map.setOnMarkerClickListener(this)

        // nastavit pozici mapy a jeji zvetseni
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(souradnice, 12f))
    }

    /*
    // udalost pro kliknuti primo na znacku
    override fun onMarkerClick(znacka: Marker): Boolean {
        if (znacka.tag == "znacka1") {
            Toast.makeText(this, "Information about Karlštejn Castle", Toast.LENGTH_SHORT).show()
        }
        return true
    }
    */
}