package cz.stokratandroid.video1

import android.content.Intent
import android.graphics.SurfaceTexture
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Surface
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        povoleniPristupuKExternimSouborum()
    }

    private var kamera: MediaRecorder? = null

    // metoda pozada o pravo pristupu k externim souborum
    private fun povoleniPristupuKExternimSouborum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent()
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.setData(uri)
                startActivity(intent)
            }
        }
    }

    private fun inicializaceKamery() {

        // nova instance MediaRecorderu
        kamera = MediaRecorder()
        kamera!!.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        kamera!!.setVideoSource(MediaRecorder.VideoSource.CAMERA)

        // nova instance bufferu
        val sft = SurfaceTexture(0)
        val sf = Surface(sft)
        kamera!!.setPreviewDisplay(sf)

        // nastaveni profilu kamery
        val cpHigh = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH)
        kamera!!.setProfile(cpHigh)

        // soubor s ulozenymi daty
        kamera!!.setOutputFile(Environment.getExternalStorageDirectory().path + "/Movies/test.mp4")
    }

    fun kameraNahravani(sender: View?) {
        if (kamera == null) {
            try {
                inicializaceKamery()
                kamera!!.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Chyba inicializace: " + e.message, Toast.LENGTH_LONG).show()
            }
            try {
                kamera!!.start()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Chyba při startu záznamu: " + e.message, Toast.LENGTH_LONG).show()
            }
            //zmenit text tlacitka
            val tlacitkoNahravani = findViewById<View>(R.id.butNahravani) as Button
            tlacitkoNahravani.text = "Konec nahrávání"
        } else {
            kamera!!.stop()
            kamera = null

            //zmenit text tlacitka
            val tlacitkoNahravani = findViewById<View>(R.id.butNahravani) as Button
            tlacitkoNahravani.text = "Nahrávání kamerou"
        }
    }

    private var prehravac: VideoView? = null

    // zahajit prehravani
    fun prehravacPrehrat(sender: View?) {
        prehravac = findViewById<View>(R.id.videoView) as VideoView
        val souborUmisteni =
            Uri.parse(Environment.getExternalStorageDirectory().path + "/Movies/test.mp4")
        prehravac!!.setVideoURI(souborUmisteni)
        prehravac!!.requestFocus()
        prehravac!!.start()
    }

    // prerusit/obnovit prehravani
    fun prehravacPauza(sender: View?) {
        if (prehravac == null) return
        if (prehravac!!.isPlaying) {
            prehravac!!.pause()
        } else {
            prehravac!!.start()
        }
    }

    // ukoncit prehravani
    fun prehravacStop(sender: View?) {
        if (prehravac == null) return
        prehravac!!.stopPlayback()
        prehravac = null
    }
}