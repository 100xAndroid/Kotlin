package cz.stokratandroid.textovezpravy

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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

        testOpravneni(42, Manifest.permission.READ_SMS)
    }

    fun nacistSMS(view: View?) {

        val SLOUPCE = arrayOf(
            "_id",
            "address",
            "body"
        )

        val cr = contentResolver
        val cur = cr.query(Uri.parse("content://sms/inbox"), SLOUPCE, null, null, null)

        val zpravySMS: MutableList<String> = ArrayList()

        while (cur!!.moveToNext()) {
            zpravySMS.add(
                String.format(
                    "[%s] %s %s",
                    cur.getString(0),
                    cur.getString(1),
                    cur.getString(2)
                )
            )
        }
        cur.close()

        var sSMS = ""
        for (zpravaSMS in zpravySMS) {
            sSMS = sSMS + zpravaSMS + "\n"
        }

        val txtSMS = findViewById<View>(R.id.txtSMS) as TextView
        txtSMS.text = sSMS
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