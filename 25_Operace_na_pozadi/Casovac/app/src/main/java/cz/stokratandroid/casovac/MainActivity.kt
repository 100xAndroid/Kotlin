package cz.stokratandroid.casovac

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }

    var casovac: CountDownTimer? = null
    fun spustitCasovac(view: View?) {
        if (casovac != null) {
            Toast.makeText(this, "Časovač je již spuštěn", Toast.LENGTH_SHORT).show()
            return
        }

        casovac = object : CountDownTimer(60000, 1000) {
            var prehravac = MediaPlayer.create(applicationContext, R.raw.pipnuti)
            override fun onTick(millisUntilFinished: Long) {
                // spustime prehravani zvuku
                if (prehravac.isPlaying) prehravac.stop()
                prehravac.start()

                // zapiseme informaci do logu
                Log.i("Casovac", "Vyvolana udalost onTick")
            }

            override fun onFinish() {
                // konec
            }
        }
        casovac!!.start()
    }

    fun ukoncitCasovac(view: View?) {
        if (casovac != null) {
            casovac!!.cancel()
            casovac = null
        }
    }
}