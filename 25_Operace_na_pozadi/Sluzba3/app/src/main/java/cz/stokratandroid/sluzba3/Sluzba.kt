package cz.stokratandroid.sluzba3

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log


class Sluzba : Service() {
    private var sluzbaBezi = false
    private var vlakno: Thread? = null
    private var dataReceiver: DataReceiver? = null
    private var prijataZprava = -1
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

        // zaregistrovat receiver kvuli prichozim zpravam
        if (dataReceiver == null) dataReceiver = DataReceiver()
        val intentFilter = IntentFilter("zpravaZAktivity")
        registerReceiver(dataReceiver, intentFilter, RECEIVER_NOT_EXPORTED)

        // vytvorit nove vlakno a sluzbu spustit
        vlakno = Thread { // na toto misto umistime kod, ktery ma sluzba zpracovavat
            val prehravac: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.pipnuti)
            // v sekundovych intervalech vypisovat do logu zaznamy
            try {
                var i = 0
                while (i < 100) {

                    // vlakno na sekundu uspat
                    Thread.sleep(1000)
                    Log.i("Sluzba test", "Sluzba bezi, krok $i")
                    if (prijataZprava > -1) {
                        i = prijataZprava
                        prijataZprava = -1
                    }

                    // odeslani zpravy ze sluzby, ocekava se, ze ji prijme aktivita
                    val broadcastIntent = Intent()
                    broadcastIntent.setPackage("cz.stokratandroid.sluzba3")
                    broadcastIntent.setAction("zpravaZeSluzby")
                    broadcastIntent.putExtra("data", i)
                    sendBroadcast(broadcastIntent)

                    // spustit prehravani zvuku
                    if (prehravac.isPlaying) prehravac.stop()
                    prehravac.start()
                    i++
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
        // odregistrujeme receiver
        if (dataReceiver != null)
            unregisterReceiver(dataReceiver)
        sluzbaBezi = false
        if (vlakno != null)
            vlakno!!.interrupt()
        Log.i("Sluzba test", "Sluzba - onDestroy")
    }

    // trida prijimajici data odeslana sluzbou
    internal inner class DataReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "zpravaZAktivity") {
                prijataZprava = intent.getIntExtra("data", -1)
            }
        }
    }
}