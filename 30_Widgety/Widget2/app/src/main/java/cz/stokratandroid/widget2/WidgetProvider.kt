package cz.stokratandroid.widget2

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.format.DateFormat
import android.widget.RemoteViews
import android.widget.Toast
import java.util.Date

class WidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val count = appWidgetIds.size
        try {
            widgetManager = appWidgetManager
            widgetIds = appWidgetIds.copyOf(appWidgetIds.size)

            // cyklus - pro kazdou existujici instanci widgetu,
            // pripojenou k tomuto provideru
            for (i in 0 until count) {
                val widgetId = widgetIds!![i]
                // vytvorit instanci layoutu widgetu
                var views: RemoteViews? = null
                try {
                    views = RemoteViews(context.packageName, R.layout.widget)

                    // vytvorit intent na tuto tridu a prida parametry
                    val clickIntent = Intent(context, WidgetProvider::class.java)
                    clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                    clickIntent.setAction("btnNastaveniKlik")

                    // vytvorit pending intent, ktery bude vyvolany po kliknuti na
                    // tlacitko Nastaveni
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        widgetId,
                        clickIntent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    views.setOnClickPendingIntent(R.id.btnNastaveni, pendingIntent)

                    // jako predchozi, ale pro tlacitko Obnovit
                    val clickIntent2 = Intent(context, WidgetProvider::class.java)
                    clickIntent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
                    clickIntent2.setAction("btnObnovitKlik")
                    val pendingIntent2 = PendingIntent.getBroadcast(
                        context,
                        widgetId,
                        clickIntent2,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                    views.setOnClickPendingIntent(R.id.btnObnovit, pendingIntent2)

                    // zmeny odeslat widget manageru widgetManager
                    widgetManager!!.updateAppWidget(widgetId, views)
                } catch (ex: Exception) {
                    Toast.makeText(context, "Chyba: " + ex.message, Toast.LENGTH_LONG).show()
                }
            }
            nacistDataZeServeru(context)
            super.onUpdate(context, appWidgetManager, appWidgetIds)
        } catch (ex: Exception) {
            Toast.makeText(context, "Fatal error: " + ex.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // podle nazvu udalosti rozlisit tlacitko
        val nazevUdalosti = intent.action
        if (nazevUdalosti == "btnNastaveniKlik") {
            otevritNastaveni(context)
        } else if (nazevUdalosti == "btnObnovitKlik") {
            Toast.makeText(context, "Počasí bude aktualizováno.", Toast.LENGTH_SHORT).show()
            nacistDataZeServeru(context)
            Toast.makeText(context, "Počasí bylo aktualizováno.", Toast.LENGTH_SHORT).show()
        }
    }

    // otevre MainActivity, ktera slouzi pro nastaveni widgetu
    private fun otevritNastaveni(context: Context) {
        // intent na tridu MainActivity a jeho spusteni
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val cn = ComponentName(context, MainActivity::class.java)
        intent.setComponent(cn)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    // nacte data ze serveru a zobrazi je v textovem poli
    private fun nacistDataZeServeru(context: Context) {

        // spustit nacitani dat o pocasi
        if (datovePripojeni(context) == true) {
            var getData: GetData? = null
            getData = GetData(context, nastaveniMista(context))
            getData.startAsync()
        }
    }

    // Overi dostupnost datoveho pripojeni
    private fun datovePripojeni(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm == null) {
            zobrazitText(context, "Datové připojení nedostupné.")
            return false
        }

        // API verze 21 a vyssi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
            ) return true
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
        zobrazitText(context, "Datové připojení nedostupné.")
        return false
    }

    companion object {
        private var widgetManager: AppWidgetManager? = null
        private var widgetIds: IntArray? = null

        // zobrazi v textovem poli informaci
        @JvmStatic
        fun zobrazitText(context: Context, text: String?) {
            val remoteViews = RemoteViews(context.packageName, R.layout.widget)
            remoteViews.setTextViewText(R.id.txtPocasi, text)
            val aktualniCas = DateFormat.format("d.M. HH:mm", Date()).toString()
            remoteViews.setTextViewText(R.id.txtDatum, nastaveniMista(context) + ", " + aktualniCas)
            val name = ComponentName(context, WidgetProvider::class.java)
            val manager = AppWidgetManager.getInstance(context)
            manager.updateAppWidget(name, remoteViews)

            /*
            val count: Int = widgetIds.length
            for (i in 0 until count) {
                val widgetId: Int = widgetIds.get(i)
                widgetManager.updateAppWidget(widgetId, remoteViews)
            }
            */
        }

        // nacte ze sdilenych nastaveni nazev mesta
        private fun nastaveniMista(context: Context): String? {
            // otevrit soubor pro cteni preferenci
            val sharedPreferences = context.getSharedPreferences("Nastaveni", Context.MODE_PRIVATE)
            // nacist ulozenou hodnotu
            return sharedPreferences.getString("misto", "Karlovy Vary")
        }
    }
}