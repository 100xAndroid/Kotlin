package cz.stokratandroid.kalendar

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

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


        testOpravneni()
    }

    fun nacistKalendare(view: View?) {

        // sloupce ktere chceme vypsat
        val SLOUPCE = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.IS_PRIMARY,
            CalendarContract.Calendars.VISIBLE,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT
        )
        val kalendare: MutableList<String> = ArrayList()
        try {
            val PODMINKA = CalendarContract.Calendars.IS_PRIMARY + " = ?"
            val PODMINKA_HODNOTY = arrayOf("1")
            val RAZENI = CalendarContract.Calendars.DEFAULT_SORT_ORDER
            val contentResolver = this.contentResolver
            val cursor = contentResolver.query(
                CalendarContract.Calendars.CONTENT_URI,
                SLOUPCE,
                PODMINKA,
                PODMINKA_HODNOTY,
                RAZENI
            )
            if (cursor!!.count > 0) {
                while (cursor.moveToNext()) {
                    println(
                        String.format(
                            "Id: %s, základní kalendář %s, viditelný %s, název: %s",
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                        )
                    )
                    kalendare.add(String.format("%s: %s", cursor.getString(0), cursor.getString(3)))
                }
            }
            cursor.close()
        } catch (ex: AssertionError) {
            ex.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var seznamKalendaru = ""
        for (kalendar in kalendare) {
            seznamKalendaru = seznamKalendaru + kalendar + "\n"
        }
        val txtKalendar = findViewById<View>(R.id.txtKalendare) as TextView
        txtKalendar.text = seznamKalendaru
    }

    fun nacistUpozorneni(view: View?) {
            val SLOUPCE = arrayOf(
                CalendarContract.CalendarAlerts.EVENT_ID,
                CalendarContract.CalendarAlerts.BEGIN,
                CalendarContract.CalendarAlerts.TITLE,
                CalendarContract.CalendarAlerts.ORGANIZER
            )
        val contentResolver = this.contentResolver
        val cursor = contentResolver.query(
            CalendarContract.CalendarAlerts.CONTENT_URI,
            SLOUPCE,
            null,
            null,
            null
        )
        val lstUpozorneni: MutableList<String> = ArrayList()

        // prochazeni dat, jejich vypis do console a ulozeni do seznamu
        while (cursor!!.moveToNext()) {
            val upozorneniID = cursor.getLong(0)
            val zacatek = cursor.getLong(1)
            val nazev = cursor.getString(2)
            val kalendar = cursor.getString(3)

            val calendarInfo = String.format(
                "Upozorneni ID: %s\nZacatek: %s\nNazev: %s\nKalendar: %s",
                upozorneniID,
                zacatek,
                nazev,
                kalendar
            )
            println(calendarInfo)

            val df: DateFormat = SimpleDateFormat("dd.MM. HH:mm")
            lstUpozorneni.add(String.format("[%s] %s %s", upozorneniID, df.format(zacatek), nazev))
        }
        cursor.close()

        var seznamUpozorneni = ""
        for (upozorneni in lstUpozorneni) {
            seznamUpozorneni = seznamUpozorneni + upozorneni + "\n"
        }
        val txtUpozorneni = findViewById<View>(R.id.txtUpozorneni) as TextView
        txtUpozorneni.text = seznamUpozorneni
    }

    var OPRAVNENI_ZADOST = 1
    private fun testOpravneni() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CALENDAR),
                OPRAVNENI_ZADOST
            )
        }
    }

    // tato metoda je zde jenom pro ilustraci, v prikladu neni pouzita
    fun nacistUdalosti(view: View?) {
        val SLOUPCE = arrayOf(
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.ORGANIZER
        )

        // Omezeni na rozsah vracenych zaznamu podle datumu od-do
        val beginTime = Calendar.getInstance()
        beginTime[2021, 9, 23, 8] = 0
        val startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime[2022, 1, 24, 8] = 0
        val endMillis = endTime.timeInMillis
        val builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, startMillis)
        ContentUris.appendId(builder, endMillis)

        /*
        // omezeni na konkretni kalendar
        val selection = CalendarContract.Instances.CALENDAR_ID + " = 3"
        val selectionArgs = arrayOf("207")
        */
        val cursor = contentResolver.query(builder.build(), SLOUPCE, null, null, null)
        val lstUdalosti: MutableList<String> = ArrayList()

        // prochazeni dat, jejich vypis do console a ulozeni do seznamu
        while (cursor!!.moveToNext()) {
            val udalostID = cursor.getLong(0)
            val zacatek = cursor.getLong(1)
            val nazev = cursor.getString(2)
            val kalendar = cursor.getString(3)
            val calendarInfo = String.format(
                "Udalost ID: %s\nZacatek: %s\nNazev: %s\nKalendar: %s",
                udalostID,
                zacatek,
                nazev,
                kalendar
            )
            println(calendarInfo)
            val df: DateFormat = SimpleDateFormat("dd.MM. HH:mm")
            lstUdalosti.add(String.format("[%s] %s %s", udalostID, df.format(zacatek), nazev))
        }
        cursor.close()
        var seznamUdalosti = ""
        for (udalost in lstUdalosti) {
            seznamUdalosti = seznamUdalosti + udalost + "\n"
        }
        val txtUpozorneni = findViewById<View>(R.id.txtUpozorneni) as TextView
        txtUpozorneni.text = seznamUdalosti
    }

    // tato metoda je zde jenom pro ilustraci, v prikladu neni pouzita
    fun zjistitPrimarniKalendar() {
        val EVENT_PROJECTION = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT
        )
        val contentResolver = contentResolver
        val selection =
            CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1"
        val cur = contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            EVENT_PROJECTION,
            selection,
            null,
            null
        )
        val calendarInfos = ArrayList<String>()
        while (cur!!.moveToNext()) {
            var calID: Long = 0
            var displayName: String? = null
            var accountName: String? = null
            var ownerName: String? = null

            // Get the field values
            calID = cur.getLong(0)
            displayName = cur.getString(1)
            accountName = cur.getString(1)
            ownerName = cur.getString(3)
            val calendarInfo = String.format(
                "Calendar ID: %s\nDisplay Name: %s\nAccount Name: %s\nOwner Name: %s",
                calID,
                displayName,
                accountName,
                ownerName
            )
            println("Id: $calendarInfo")
        }
    }
}