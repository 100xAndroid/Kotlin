package cz.stokratandroid.internet1

import android.os.Handler
import android.os.Looper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class GetData {
    var nactenaData = ""

    // operace, ktera bude spustena na pozadi
    fun startAsync(): Void? {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
                val url = URL("https://www.jsonkeeper.com/b/XFIM")
                val conn = url.openConnection() as HttpsURLConnection
                val stream = conn.inputStream
                val buff = BufferedReader(InputStreamReader(stream))
                var radka: String? = ""
                do {
                    nactenaData = nactenaData + radka
                    radka = buff.readLine()
                } while (radka != null)
            } catch (e: Exception) {
                // MainActivity.jsonTextView.setText(e.getMessage());
                nactenaData = "Chyba: " + e.message
            }
            handler.post { MainActivity!!.jsonTextView!!.setText(nactenaData) }
        }
        return null
    }
}
