package cz.stokratandroid.gps2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), LocationListener {
    protected var locationManagerGps: LocationManager? = null
    protected var locationManagerNet: LocationManager? = null
    private var txtGpsSirka: TextView? = null
    private var txtGpsDelka: TextView? = null
    private var txtGpsVyska: TextView? = null
    private var txtGpsPresnost: TextView? = null
    private var txtSitSirka: TextView? = null
    private var txtSitDelka: TextView? = null
    private var txtSitVyska: TextView? = null
    private var txtSitPresnost: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtGpsSirka = findViewById<View>(R.id.txtGpsSirka) as TextView
        txtGpsDelka = findViewById<View>(R.id.txtGpsDelka) as TextView
        txtGpsVyska = findViewById<View>(R.id.txtGpsVyska) as TextView
        txtGpsPresnost = findViewById<View>(R.id.txtGpsPresnost) as TextView
        locationManagerGps = getSystemService(LOCATION_SERVICE) as LocationManager
        txtSitSirka = findViewById<View>(R.id.txtSitSirka) as TextView
        txtSitDelka = findViewById<View>(R.id.txtSitDelka) as TextView
        txtSitVyska = findViewById<View>(R.id.txtSitVyska) as TextView
        txtSitPresnost = findViewById<View>(R.id.txtSitPresnost) as TextView
        locationManagerNet = getSystemService(LOCATION_SERVICE) as LocationManager

        /*
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "Nejsou prava", Toast.LENGTH_SHORT)
        }
        // Parameters :
        //   First(provider)    :  the name of the provider with which to register
        //   Second(minTime)    :  the minimum time interval for notifications,
        //                         in milliseconds. This field is only used as a hint
        //                         to conserve power, and actual time between location
        //                         updates may be greater or lesser than this value.
        //   Third(minDistance) :  the minimum distance interval for notifications, in meters
        //   Fourth(listener)   :  a {#link LocationListener} whose onLocationChanged(Location)
        //                         method will be called for each location update
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this)
                */
        /*
        val location: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val msg = "New Latitude: " + location.latitude + "New Longitude: " + location.longitude
            Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
        }
        */
        // Getting GPS status
        // isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        // Getting network status
        // isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onResume() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(
                baseContext,
                "Aplikace nemá povolené zjišťování polohy.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // Parametery :
            //   provider    -  zpusob ziskavani informaci o poloze (GPS nebo sit)
            //   minTime     -  interval ziskavani informaci o poloze (v milisekundach). Cim je cas
            //                  kratsi, tim je poloha presnejsi ale zaroven vyssi spotreba baterie
            //   minDistance -  nejmensi zmena vzdalenosti, aby byla vyvolana udalost onLocationChanged
            //   listener    -  urcuje, ktera udalost onLocationChanged bude pri zmene polohy volana
            locationManagerGps!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                2000,
                1f,
                this
            )
            locationManagerNet!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                2000,
                1f,
                this
            )
        }
        super.onResume()
    }

    override fun onPause() {
        locationManagerGps!!.removeUpdates(this)
        locationManagerNet!!.removeUpdates(this)
        super.onPause()
    }

    override fun onLocationChanged(location: Location) {
        zobrazitSouradnice(location)
    }

    override fun onProviderDisabled(provider: String) {
        // zobrazit uzivateli Nastaveni polohy
        // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        // startActivity(intent);
        if (provider == LocationManager.GPS_PROVIDER) {
            val txtGpsSirka = findViewById<View>(R.id.txtGps) as TextView
            txtGpsSirka.text = "GPS (vypnuto)"
        } else if (provider == LocationManager.NETWORK_PROVIDER) {
            val txtGpsSirka = findViewById<View>(R.id.txtSit) as TextView
            txtGpsSirka.text = "Mobilní síť (vypnuto)"
        }
    }

    override fun onProviderEnabled(provider: String) {
        if (provider == LocationManager.GPS_PROVIDER) {
            val txtGpsSirka = findViewById<View>(R.id.txtGps) as TextView
            txtGpsSirka.text = "GPS"
        } else if (provider == LocationManager.NETWORK_PROVIDER) {
            val txtGpsSirka = findViewById<View>(R.id.txtSit) as TextView
            txtGpsSirka.text = "Mobilní síť"
        }
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    // zobrazi ziskane souradnice
    private fun zobrazitSouradnice(loc: Location) {
        if (loc.provider == LocationManager.GPS_PROVIDER) {
            txtGpsSirka!!.text = "Zeměpisná šířka: " + loc.latitude
            txtGpsDelka!!.text = "Zeměpisná délka: " + loc.longitude
            txtGpsVyska!!.text = "Nadmořská výška: " + loc.altitude
            txtGpsPresnost!!.text = "Přesnost: " + loc.accuracy
        } else {
            txtSitSirka!!.text = "Zeměpisná šířka: " + loc.latitude
            txtSitDelka!!.text = "Zeměpisná délka: " + loc.longitude
            txtSitVyska!!.text = "Nadmořská výška: " + loc.altitude
            txtSitPresnost!!.text = "Přesnost: " + loc.accuracy
        }
        try {
            val myLocation = Geocoder(applicationContext, Locale.getDefault())
            val adresy = myLocation.getFromLocation(loc.latitude, loc.longitude, 1)
            if (adresy!!.size > 0) {
                val txtMisto = findViewById<View>(R.id.txtMisto) as TextView
                val adresa = adresy[0]
                val misto = (adresa.thoroughfare + " "
                        + adresa.featureName + ", "
                        + adresa.locality + ", "
                        + adresa.countryCode)
                txtMisto.text = misto
            }
        } catch (e: IOException) {
        }
    }
}