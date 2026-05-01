package cz.stokratandroid.sluzba1

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class Sluzba : Service() {
    private var sluzbaBezi = false
    private var vlakno: Thread? = null
    override fun onCreate() {
        Log.i("Sluzba test", "Service onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // test jestli je sluzba uz spustena, pokud ano, znovu nespoustet
        if (sluzbaBezi) {
            Log.i("Sluzba test", "Sluzba je uz spustena.")
            return START_STICKY
        } else {
            sluzbaBezi = true
            Log.i("Sluzba test", "Sluzba - onStartCommand")
        }


        // Vytvorit nove vlakno a sluzbu spustit
        vlakno = Thread { // na toto misto umistime kod, ktery ma sluzba zpracovavat
            val prehravac = MediaPlayer.create(applicationContext, R.raw.pipnuti)
            // v sekundovych intervalech vypisovat do logu zaznamy
            try {
                for (i in 0..99) {
                    // vlakno na sekundu uspat
                    Thread.sleep(1000)
                    Log.i("Sluzba test", "Sluzba bezi, krok $i")

                    // spustit prehravani zvuku
                    if (prehravac.isPlaying)
                        prehravac.stop()
                    prehravac.start()
                }
            } catch (e: InterruptedException) {
                Log.i("Sluzba test", "Vyjimka InterruptedException")
                stopSelf()
            } catch (e: Exception) {
                Log.i("Sluzba test", "Vyjimka: " + e.message)
            }
            // sluzba ukoncila svoji cinnost, bude zastavena
            stopSelf()
        }
        vlakno!!.start()
        return START_STICKY
    }

    override fun onBind(arg0: Intent): IBinder? {
        Log.i("Sluzba test", "Sluzba - onBind")
        return null
    }

    override fun onDestroy() {
        sluzbaBezi = false
        if (vlakno != null) vlakno!!.interrupt()
        Log.i("Sluzba test", "Sluzba - onDestroy")
    }
}
