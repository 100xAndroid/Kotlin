package cz.stokratandroid.opravneni

import android.Manifest
import cz.stokratandroid.opravneni.R
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val OPRAVNENI_ZADOST = 1

    // metoda volana kliknutim na tlacitko Test opravneni
    fun testOpravneni(view: View?) {
        // test, jestli uzivatel opravneni na odesilani SMS jiz drive udelil
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // test, jestli muzeme uzivatele o opravneni pozadat
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
                Toast.makeText(this, "Oprávnění nebylo uděleno.", Toast.LENGTH_LONG).show()
            } else {
                // pozadame uzivatele o udeleni opravneni odesilat SMS
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    OPRAVNENI_ZADOST
                )
            }
        } else {
            Toast.makeText(this, "Oprávnění je k dispozici.", Toast.LENGTH_LONG).show()
        }
    }
}