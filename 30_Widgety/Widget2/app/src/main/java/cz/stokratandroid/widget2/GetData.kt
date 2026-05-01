package cz.stokratandroid.widget2

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import cz.stokratandroid.widget2.WidgetProvider.Companion.zobrazitText
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class GetData(var context: Context, nazevMesta: String?) {
    var mesto = "Karlovy Vary"
    var data = ""

    init {
        mesto = nazevMesta!!.replace(" ", "%20") // nahradit pripadne mezery
    }

    // operace, ktera bude spustena na pozadi
    fun startAsync(): Void? {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
        //        val mesto = MainActivity.txtMesto?.text.toString().replace(" ", "%20")
                val url = URL(
                    "https://api.openweathermap.org/data/2.5/weather?q="
                            + mesto
                            + "&units=metric&lang=cz&appid=df15c19897e64a504b06ce36ae0dce85"
                )
                val conn = url.openConnection() as HttpsURLConnection
                conn.requestMethod = "GET" // pro predani dat pouzita metoda GET
                conn.readTimeout = 10000 // timeout v milisekundach
                conn.connectTimeout = 15000 // timeout v milisekundach
                conn.doOutput = false // priznak - telo zpravy neodesilat
                val stream = conn.inputStream
                val buff = BufferedReader(InputStreamReader(stream))

                // nacteni dat po jednotlivych radcich
                var radka: String? = ""
                while (radka != null) {
                    radka = buff.readLine()
                    data = data + radka
                }
            } catch (e: Exception) {
                zobrazitText(context, e.message)
            }
            handler.post {
                val dekodovanaData = dekodovatJson(data)
                zobrazitText(context, dekodovanaData)
            }
        }
        return null
    }

    /*
    // metoda se zavola po skonceni operace
    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        val dekodovanaData = dekodovatJson(data)
        zobrazitText(context, dekodovanaData)
    }
     */

    // rozparsovani dat z formatu JSON
    private fun dekodovatJson(data: String): String? {
        return try {
            // parsovani teploty
            var jObj = JSONObject(data)
            jObj = JSONObject(jObj.getString("main"))
            val teplota = jObj.getString("temp")
            // prevod na cislo, abychom mohli nastavit potrebny pocet desetinnych mist
            val teplotaNum = teplota.toDouble()

            // parsovani popisu pocasi
            jObj = JSONObject(data)
            val jArray = JSONArray(jObj.getString("weather"))
            jObj = JSONObject(jArray.getString(0))
            val text = jObj.getString("description")
            String.format("%s, %.1f°C", text, teplotaNum)
        } catch (e: JSONException) {
            e.message
        }
    }
}
