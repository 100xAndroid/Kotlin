package cz.stokratandroid.mediaplayer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast

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

        inicializacePosuvniku()
    }

    private var vyzvaneni: Ringtone? = null

    // prehraje zvuk vyzvaneni
    fun prehratZvuk1(sender: View?) {
        if (vyzvaneni == null) {
            val vyzvaneniUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            vyzvaneni = RingtoneManager.getRingtone(applicationContext, vyzvaneniUri)
            vyzvaneni!!.play()
        } else {
            vyzvaneni!!.stop()
            vyzvaneni = null
        }
    }

    // prehraje zvuk klinuti
    fun prehratZvuk2(sender: View?) {

        // spustime prehravani zvuku
        val prehravac = MediaPlayer.create(this, R.raw.kliknuti)
        prehravac.start()
        Toast.makeText(applicationContext, "Stisknuto tlačítko", Toast.LENGTH_SHORT).show()
    }

    // prehraje zvuk
    fun prehratZvuk3(sender: View?) {

        // spustime prehravani zvuku
        val prehravac = MediaPlayer.create(this, R.raw.zvuk)
        prehravac.start()

        // nastavime listener pro konec prehravani
        prehravac.setOnCompletionListener {
            Toast.makeText(
                applicationContext, "Konec přehrávání", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private var prehravac: MediaPlayer? = null
    private var posuvnik: SeekBar? = null
    private val handler = Handler()

    // nastaveni listeneru posuvniku
    // jakmile uzivatel posune ukazatelem, upravime pozici v prehravane skladbe
    private fun inicializacePosuvniku() {
        // najdeme posuvnik ve formulari
        posuvnik = findViewById<View>(R.id.seekBar) as SeekBar
        // vytvorime listener ktery ceka na zmenu polohy ukazatele
        posuvnik!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, pozice: Int, odUzivatele: Boolean) {
                if (prehravac == null) return
                if (odUzivatele) prehravac!!.seekTo(pozice)
            }
        })
    }

    // spusti prehravani
    fun prehravacHrat(sender: View?) {
        if (prehravac != null) prehravac!!.reset()

        // nastavit zvukovy soubor a spustit prehravac
        prehravac = MediaPlayer.create(this, R.raw.android_latest_2013)
        prehravac!!.start()

        // iniciace posuvniku
        val posuvnik = findViewById<View>(R.id.seekBar) as SeekBar
        posuvnik.max = prehravac!!.getDuration()
        // Toast.makeText(getApplicationContext(), "Delka skladby: " + prehravac.getDuration(), Toast.LENGTH_SHORT).show();
        runOnUiThread(object : Runnable {
            override fun run() {
                nastavitPosuvnik()
                handler.postDelayed(this, 100)
            }
        })
    }

    // nastavit polohu posuvniku podle pozice v prehravane skladbe
    private fun nastavitPosuvnik() {
        if (prehravac != null) posuvnik!!.progress =
            prehravac!!.currentPosition else posuvnik!!.progress = 0
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
        prehravac!!.reset()
        prehravac = null
    }
}