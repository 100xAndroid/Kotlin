package cz.stokratandroid.toast

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    // nejjednodussi verze toast zpravy
    fun toastDialog1(sender: View?) {
        // Toast.makeText(applicationContext, "Toast-test", Toast.LENGTH_LONG).show()
        /*val kontext = applicationContext
        val zprava = "Toast-test"
        val trvani = Toast.LENGTH_LONG
        val toast = Toast.makeText(kontext, zprava, trvani)
        toast.show()
*/
        Toast.makeText(applicationContext, "Toast-test", Toast.LENGTH_LONG).show()
    }

    // toast zprava se zmenenym umistenim na displeji
    // POZOR, OD API VERZE 30 NEPODPOROVANO
    fun toastDialog2(sender: View?) {
        val toast = Toast.makeText(applicationContext, "Toast-test", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.TOP or Gravity.END, 100, 450)
        toast.show()
    }

    // toast zprava s vlastnim layoutem
    // POZOR, OD API VERZE 30 NEPODPOROVANO
    fun toastDialog3(sender: View?) {
        // ziskat formular
        val inflater = layoutInflater
        val layout = inflater.inflate(
            R.layout.uzivatelsky_toast,
            findViewById<View>(R.id.custom_toast_container) as ViewGroup
        )

        // doplneni textu do poli formulare
        val text1 = layout.findViewById<View>(R.id.text1) as TextView
        text1.text = "Toast - test"
        val text2 = layout.findViewById<View>(R.id.text2) as TextView
        text2.text = "Víceřádková, uživatelsky definovaná, toast zpráva."

        // zobrazeni toast zpravy
        val toast = Toast.makeText(applicationContext, "Toast-test", Toast.LENGTH_LONG)
        toast.view = layout
        toast.show()
    }
}