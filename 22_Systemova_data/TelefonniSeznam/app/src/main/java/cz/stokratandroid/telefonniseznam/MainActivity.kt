package cz.stokratandroid.telefonniseznam

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

        testOpravneni(42, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
        nacistKontakty(null)
    }

    fun nacistKontakty(view: View?) {
        val SLOUPCE_KONTAKT = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )

        val SLOUPCE_TEL = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val kontakty: MutableList<String> = ArrayList()
        val cr = contentResolver
        val cur1 =
            cr.query(ContactsContract.Contacts.CONTENT_URI, SLOUPCE_KONTAKT, null, null, null)

        while (cur1!!.moveToNext()) {
            val id = cur1.getString(0)
            val jmeno = cur1.getString(1)
            println("Jmeno : $jmeno, ID : $id")

            // nacteni kontaktnich udaju (tel. cisel)
            val cur2 = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                SLOUPCE_TEL,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
            )

            // prochazeni nalezenych kontaktnich udaju (tel. cisel)
            while (cur2!!.moveToNext()) {
                val phone = cur2.getString(0)
                kontakty.add(String.format("[%s] %s %s", id, jmeno, phone))
            }
            cur2.close()
        }

        var sKontakty = ""
        for (kontakt in kontakty) {
            sKontakty = sKontakty + kontakt + "\n"
        }

        val txtKontakty = findViewById<View>(R.id.txtKontakty) as TextView
        txtKontakty.text = sKontakty

        cur1.close()
    }

    private fun testOpravneni(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(
                this,
                p
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(this, permissionsId, callbackId)
    }
}