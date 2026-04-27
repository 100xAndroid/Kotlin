package cz.stokratandroid.alert

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    }

    // zakladni alert dialog s jednim tlacitkem
    fun alertDialog01(sender: View?) {
        // vytvorit instanci tridy AlertDialog
        val builder = AlertDialog.Builder(this)
        // nastaveni parametru dialogu
        builder.setTitle("Záhlaví")
        builder.setMessage("Test - alert dialog")
        builder.setPositiveButton("Zavřít", null)

        // zobrazeni dialogu
        builder.create().show()
    }

    // dialog vyvola rutinu pri kliknuti na tlacitko
    fun alertDialog02(sender: View?) {
        // vytvorit instanci tridy AlertDialog
        val builder = AlertDialog.Builder(this)
        // nastavit parametru dialogu
        builder.setTitle("Záhlaví")
        builder.setMessage("Test - alert dialog")
        // definice listeneru pro tlačítko
        val butListener = DialogInterface.OnClickListener { dialog, id ->
            Toast.makeText(
                applicationContext, "Stisknuto tlačítko Zavřít", Toast.LENGTH_LONG
            ).show()
        }
        builder.setPositiveButton("Zavřít", butListener)
        // zobrazit dialog
        builder.create().show()
    }

    // dialog zobrazi uzivatelsky formular
    fun alertDialog03(sender: View?) {
        // vytvorit instanci tridy AlertDialog
        val builder = AlertDialog.Builder(this)

        // serializace formular
        val inflater = layoutInflater
        val alertLayout = inflater.inflate(R.layout.uzivatelsky_alert, null)

        // nastaveni parametru dialogu a tlacitka
        builder.setTitle("Záhlaví")
        builder.setMessage("Test - alert dialog")
        builder.setPositiveButton("OK", null)
        builder.setNegativeButton("Zrušit", null)
        builder.setNeutralButton("Nic", null)
        // vlozit do dialogu pripraveny formular
        builder.setView(alertLayout)

        // zobrazit dialog
        builder.create().show()
    }

    // dialog zobrazi seznam polozek
    fun alertDialog10(sender: View?) {
        // pole nazvu polozek seznamu
        val poleNazvu = arrayOf("Volba 1", "Volba 2", "Volba 3", "Volba 4")

        // vytvorit instanci tridy AlertDialog
        val builder = AlertDialog.Builder(this)

        // nastaviit zahlavi dialogu
        builder.setTitle("Záhlaví")

        // nastavit seznam a listener pro kliknuti
        builder.setItems(poleNazvu) { dialog, volba ->
            Toast.makeText(
                applicationContext,
                "Zvolena možnost č. $volba",
                Toast.LENGTH_LONG
            ).show()
        }

        // zobrazit dialog
        builder.create().show()
    }

    // pole, do ktereho se budou ukladat vybrane polozky
    var zvolenePolozky = ArrayList<Int>()

    // dialog zobrazi zaskrtavaci seznam polozek
    fun alertDialog11(sender: View?) {
        // pole nazvu seznamu
        val poleNazvu = arrayOf("Volba 1", "Volba 2", "Volba 3", "Volba 4")

        // vytvorit instanci tridy AlertDialog
        val builder = AlertDialog.Builder(this)

        // nastavit zahlavi dialogu
        builder.setTitle("Záhlaví")

        // nastavit seznam a listener pro kliknuti
        builder.setMultiChoiceItems(poleNazvu, null) { dialog, volba, zaskrtnuto ->
            if (zaskrtnuto) {
                // pri zaskrtnuti pridat do seznamu
                zvolenePolozky.add(volba)
            } else if (zvolenePolozky.contains(volba)) {
                // pri odskrtnuti vyradit ze seznamu
                zvolenePolozky.remove(Integer.valueOf(volba))
            }
        }

        // listener pro tlačítko
        val butListener = DialogInterface.OnClickListener { dialog, id ->
            Toast.makeText(
                applicationContext,
                "Zvolené položky: $zvolenePolozky",
                Toast.LENGTH_LONG
            ).show()
        }
        builder.setPositiveButton("Hotovo", butListener)

        // zobrazit dialog
        builder.create().show()
    }

    // dialog nastaveni data
    fun alertDialog20(sender: View?) {

        // nastavit datum, napr. 1.1.2040
        val iRok = 2040
        val iMesic = 0 // pozor, mesice se v Kotlin kalendari cisluji od 0
        val iDen = 1

        // definujeme listener pro zvolene datum v dialogu
        val datumListener = OnDateSetListener { view, rok, mesic, den ->
            val strVysledek = String.format("Zvolené datum %d.%d.%d", den, mesic, rok)
            Toast.makeText(applicationContext, strVysledek, Toast.LENGTH_LONG).show()
        }

        // vytvorit instanci dialogu
        val datePickerDialog = DatePickerDialog(this, datumListener, iRok, iMesic, iDen)
        // zobrazit dialog
        datePickerDialog.show()
    }

    // dialog nastaveni casu
    fun alertDialog21(sender: View?) {

        // ziskat aktualni cas
        val kalendar = Calendar.getInstance()
        val iHodina = kalendar[Calendar.HOUR_OF_DAY]
        val iMinuta = kalendar[Calendar.MINUTE]

        // listener pro zvoleny cas v dialogu
        val casListener = OnTimeSetListener { view, hod, min ->
            val strVysledek = String.format("Zvolený čas %d:%d", hod, min)
            Toast.makeText(applicationContext, strVysledek, Toast.LENGTH_LONG).show()
        }

        // vytvorit instanci dialogu
        val timePickerDialog = TimePickerDialog(this, casListener, iHodina, iMinuta, true)
        // zobrazit dialog
        timePickerDialog.show()
    }

    // dialog nastaveni data v ruznych barevnych tematech
    fun alertDialogTema1(sender: View) {
        var iTema = android.R.style.Theme_DeviceDefault_Dialog_Alert

            // volba stylu, podle tlacitka, na které uzivatel kliknul
        val butId = sender.id
        if (butId == R.id.button30)
            iTema = android.R.style.Theme_DeviceDefault_Dialog_Alert
        else if (butId == R.id.button31)
            iTema = android.R.style.Theme_DeviceDefault_Light_Dialog_Alert
        else if (butId == R.id.button32)
            iTema = android.R.style.Theme_Holo_Light_Panel
        else if (butId == R.id.button40)
            iTema = R.style.datepicker

        // nastavit datum, napr. 1.1.2040
        val iRok = 2040
        val iMesic = 0 // pozor, mesice se v Kotlin kalendari cisluji od 0
        val iDen = 1

        // vytvorit instanci dialogu
        val datePickerDialog = DatePickerDialog(this, iTema, null, iRok, iMesic, iDen)
        // zobrazt dialog
        datePickerDialog.show()
    }
}