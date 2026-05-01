package cz.stokratandroid.vlakno

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var vlakno: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun spustitVlakno(view: View?) {
        if (vlakno != null) {
            Toast.makeText(this, "Vlákno je již spuštěno", Toast.LENGTH_SHORT).show()
            return
        }
        vlakno = Thread {
            val prehravac = MediaPlayer.create(applicationContext, R.raw.pipnuti)
            try {
                for (i in 0..99) {

                    // spustit prehravani zvuku
                    if (prehravac.isPlaying) prehravac.stop()
                    prehravac.start()

                    // zapisat informaci do logu
                    Log.i("Vlakno", "Vlakno bezi, krok $i")

                    // zastavit vlakno na 1 sekundu
                    Thread.sleep(1000)
                }
            } catch (e: Exception) {
                Log.i("Sluzba test", "Vyjimka: " + e.message)
            }
        }
        vlakno!!.start()
    }

    fun ukoncitVlakno(view: View?) {
        if (vlakno != null) {
            vlakno!!.interrupt()
            vlakno = null
        }
    }
}