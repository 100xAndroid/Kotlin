package cz.stokratandroid.video2

import android.content.Intent
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.CamcorderProfile
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {

    private var mediaRecorder: MediaRecorder? = null
    private var prehravac: MediaPlayer? = null
    private var sface: Surface? = null

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

        val textureView = findViewById<View>(R.id.textureView) as TextureView
        textureView.surfaceTextureListener = this
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        sface = Surface(surface)
        inicializacePrehravace()
    }

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

    private fun inicializacePrehravace() {
        // kontrola souboru s ulozenym videem
        val file = File(Environment.getExternalStorageDirectory().path + "/Movies/test.mp4")
        if (!file.exists()) {
            Toast.makeText(this, "Soubor s videem neexistuje", Toast.LENGTH_SHORT).show()
            return
        }

        // instance prehravace a jeho spusteni
        try {
            prehravac = MediaPlayer()
            prehravac!!.setDataSource(Environment.getExternalStorageDirectory().path + "/Movies/test.mp4")
            prehravac!!.setSurface(sface)
            prehravac!!.prepareAsync()
            /*
            prehravac!!.setOnBufferingUpdateListener(this)
            prehravac!!.setOnCompletionListener(this)
            prehravac!!.setOnPreparedListener(this)
            prehravac!!.setOnVideoSizeChangedListener(this)
            */

            prehravac!!.setAudioStreamType(
                AudioManager.STREAM_MUSIC
            )
            prehravac!!.setOnPreparedListener {
                prehravac!!.start()
                // prehravac!!.setLooping(true)
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onSurfaceTextureSizeChanged(surfaceTexture: SurfaceTexture, i: Int, i1: Int) {}
    override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
        return false
    }

    override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {}
    private fun inicializaceKamery() {

        // nova instance MediaRecorderu + jeho nastaveni
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.CAMERA)
        mediaRecorder!!.setOrientationHint(90)

        // nova instance bufferu
        val sft = SurfaceTexture(0)
        val sf = Surface(sft)
        mediaRecorder!!.setPreviewDisplay(sf)

        // nastaveni profilu kamery
        val cpHigh = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH)
        mediaRecorder!!.setProfile(cpHigh)

        // soubor s ulozenymi daty
        mediaRecorder!!.setOutputFile(Environment.getExternalStorageDirectory().path + "/Movies/test.mp4")
    }

    // zahaji/ukonci zaznam videa
    fun kameraNahravani(sender: View?) {
        // pokud bezi prehravani videa, zastavime ho
        if (prehravac != null) {
            prehravac!!.release()
            prehravac = null
        }
        val butNahravani = findViewById<View>(R.id.butNahravani) as Button
        // spusteni nahravani
        if (mediaRecorder == null) {
            try {
                inicializaceKamery()
                mediaRecorder!!.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Chyba inicializace: " + e.message, Toast.LENGTH_LONG).show()
            }
            try {
                mediaRecorder!!.start()
                butNahravani.text = "Konec nahrávání"
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Chyba při startu záznamu: " + e.message, Toast.LENGTH_LONG).show()
            }
        } else {
            mediaRecorder!!.stop()
            mediaRecorder = null
            butNahravani.text = "Nahrávání kamerou"
        }
    }

    // zahaji prehravani
    fun prehravacPrehrat(sender: View?) {
        if (prehravac != null) {
            prehravac!!.release()
            prehravac = null
        }
        inicializacePrehravace()
    }

    // prerusi/obnovi prehravani
    fun prehravacPauza(sender: View?) {
        if (prehravac == null) return
        if (prehravac!!.isPlaying) {
            prehravac!!.pause()
        } else {
            prehravac!!.start()
        }
    }

    // ukonci prehravani
    fun prehravacStop(sender: View?) {
        if (prehravac == null) return
        prehravac!!.stop()
        prehravac!!.release()
        prehravac = null
    }
}