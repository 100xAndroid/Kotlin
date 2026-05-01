package cz.stokratandroid.gps1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity(), LocationListener {

    protected var locationManager: LocationManager? = null
    private var txtSirka: TextView? = null
    private var txtDelka: TextView? = null
    private var txtVyska: TextView? = null
    private var txtPresnost: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtSirka = findViewById<View>(R.id.txtSirka) as TextView
        txtDelka = findViewById<View>(R.id.txtDelka) as TextView
        txtVyska = findViewById<View>(R.id.txtVyska) as TextView
        txtPresnost = findViewById<View>(R.id.txtPresnost) as TextView
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }


    override fun onResume() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Aplikace nemá povolené zjišťování polohy.", Toast.LENGTH_SHORT)
                .show()
        } else {
            // Parametery :
            //   provider    -  zpusob ziskavani informaci o poloze (GPS nebo sit)
            //   minTime     -  interval ziskavani informaci o poloze (v milisekundach). Cim je cas
            //                  kratsi, tim je poloha presnejsi ale zaroven vyssi spotreba baterie
            //   minDistance -  nejmensi zmena vzdalenosti, aby byla vyvolana udalost onLocationChanged
            //   listener    -  urcuje, ktera udalost onLocationChanged bude pri zmene polohy volana
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1f, this)
        }
        // zavolat původní onResume událost
        super.onResume()
    }

    override fun onPause() {
        // konec zjišťování polohy
        locationManager!!.removeUpdates(this)

        // zavolat původní onPause událost
        super.onPause()
    }

    override fun onLocationChanged(location: Location) {
        zobrazitSouradniceGps(location)
    }

    override fun onProviderDisabled(provider: String) {
        // zobrazit uzivateli Nastaveni polohy
        // val intent: Intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        // startActivity(intent)

        Toast.makeText(baseContext, "GPS je vypnuta!", Toast.LENGTH_SHORT).show()
    }

    override fun onProviderEnabled(provider: String) {
        Toast.makeText(baseContext, "GPS je zapnuta.", Toast.LENGTH_SHORT).show()
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    // zobrazit ziskane souradnice
    private fun zobrazitSouradniceGps(loc: Location) {
        txtSirka!!.text = "Zeměpisná šířka: " + loc.latitude
        txtDelka!!.text = "Zeměpisná délka: " + loc.longitude
        txtVyska!!.text = "Nadmořská výška: " + loc.altitude
        txtPresnost!!.text = "Přesnost: " + loc.accuracy
    }
}