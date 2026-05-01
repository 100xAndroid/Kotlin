package cz.stokratandroid.mediarecorder

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
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

        tlacitkoNahravani = findViewById<View>(R.id.button1) as Button
        inicializacePosuvniku()
    }

    private var zaznamnik: MediaRecorder? = null
    private var tlacitkoNahravani: Button? = null

    // nahrat zvuk z mikrofonu
    fun zaznamZvuku(sender: View?) {
        if (zaznamnik == null) {
            // zahajit zaznam zvuku z mikrofonu
            zaznamnik = MediaRecorder()
            zaznamnik!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            zaznamnik!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            zaznamnik!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            zaznamnik!!.setOutputFile("/sdcard/Music/test.amr")
            try {
                zaznamnik!!.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            zaznamnik!!.start()

            //zmenit text tlacitka
            tlacitkoNahravani!!.text = "Konec nahrávání"
        } else {
            // ukoncit zaznam zvuku z mikrofonu
            zaznamnik!!.stop()
            zaznamnik = null
            //zmenit text tlacitka
            tlacitkoNahravani!!.text = "Záznam zvuku"

            // informace o ukonceni nahravani
            Toast.makeText(applicationContext, "Konec záznamu.", Toast.LENGTH_SHORT).show()
        }
    }

    private var prehravac: MediaPlayer? = null
    private var posuvnik: SeekBar? = null
    private val mHandler = Handler()

    // nastaveni listeneru posuvniku
    // jakmile uzivatel posune ukazatelem, upravit pozici v prehravane skladbe
    private fun inicializacePosuvniku() {
        // najit posuvnik ve formulari
        posuvnik = findViewById<View>(R.id.seekBar) as SeekBar
        // vytvorit listener ktery ceka na zmenu polohy ukazatele
        posuvnik!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, pozice: Int, odUzivatele: Boolean) {
                if (prehravac == null) return
                if (odUzivatele) prehravac!!.seekTo(pozice)
            }
        })
    }

    // spustit prehravani
    fun prehravacHrat(sender: View?) {
        if (prehravac != null) prehravac!!.reset()

        // umistenni zvukoveho souboru
        val souborUmisteni =
            Uri.parse(Environment.getExternalStorageDirectory().path + "/Music/test.amr")

        // test, jestli existuje zvukovy soubor
        val zvukovySoubor = File(souborUmisteni.path)
        if (!zvukovySoubor.exists()) return

        // nastavit zvukovy soubor a spustit prehravac
        prehravac = MediaPlayer.create(this, souborUmisteni)
        prehravac!!.start()

        // iniciace posuvniku
        val posuvnik = findViewById<View>(R.id.seekBar) as SeekBar
        posuvnik.max = prehravac!!.getDuration()
        // Toast.makeText(getApplicationContext(), "Delka skladby: " + prehravac.getDuration(), Toast.LENGTH_SHORT).show();
        runOnUiThread(object : Runnable {
            override fun run() {
                nastavitPosuvnik()
                mHandler.postDelayed(this, 100)
            }
        })
    }

    // nastavit polohu posuvniku podle pozice v prehravane skladbe
    private fun nastavitPosuvnik() {
        if (prehravac != null) posuvnik!!.progress =
            prehravac!!.currentPosition else posuvnik!!.progress = 0
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
        prehravac!!.reset()
        prehravac = null
    }
}