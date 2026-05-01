package cz.stokratandroid.blesk

import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // test, jestli zarizeni obsahuje blesk
        val jeBleskPodporovan =
            applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        if (!jeBleskPodporovan) {
            Toast.makeText(applicationContext, "Zařízení nepodporuje blesk.", Toast.LENGTH_LONG)
                .show()
        }
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        cameraId = idFotoaparatuSBleskem()
    }

    private fun idFotoaparatuSBleskem(): String {
        var cameraId = ""
        /*
        // nejjednodussi zpusob - blesk ma kamera s id = 0
        return "0"
        */

        /*
        // ziskat id hlavni kamery
        try {
            cameraId = cameraManager!!.cameraIdList[0]
        } catch (e: CameraAccessException) {
            Toast.makeText(applicationContext, "Chyba: " + e.message, Toast.LENGTH_LONG).show()
        }
        */

        // Najit fotoaparat s bleskem a zjistit jeho id.
        // Projit postupne vsechny fotoaparaty dostupne v zarizeni..
        try {
            val fotoaparaty = cameraManager!!.cameraIdList
            if (fotoaparaty.size == 0) {
                Toast.makeText(
                    applicationContext,
                    "Zařízení neobsahuje fotoaparát.",
                    Toast.LENGTH_LONG
                ).show()
                return ""
            }
            for (camId in fotoaparaty) {
                val characteristics = cameraManager!!.getCameraCharacteristics(camId)
                // zjistit, jestli ma blesk
                val maBlesk = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                // zjistit jestli je blesk vzadu, vepredu nebo jestli je externi
                val umisteniBlesku = characteristics.get(CameraCharacteristics.LENS_FACING)
                // pokud ma blesk, zapamatovat si jeho id
                if (maBlesk!!) cameraId = camId
            }
        } catch (ex: CameraAccessException) {
            Toast.makeText(applicationContext, "Chyba: " + ex.message, Toast.LENGTH_LONG).show()
        }
        if (cameraId.isEmpty()) {
            Toast.makeText(applicationContext, "Zařízení neobsahuje blesk.", Toast.LENGTH_LONG)
                .show()
        }
        return cameraId
    }

    // rozsvitit blesk fotoaparatu
    fun rozsvitit_click(view: View?) {
        try {
            cameraManager!!.setTorchMode(cameraId!!, true)
            Toast.makeText(applicationContext, "Blesk je zapnutý", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Chyba: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    // zhasnout blesk fotoaparatu
    fun zhasnout_click(view: View?) {
        try {
            cameraManager!!.setTorchMode(cameraId!!, false)
            Toast.makeText(applicationContext, "Blesk je vypnutý", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Chyba: " + e.message, Toast.LENGTH_LONG).show()
        }
    }
}