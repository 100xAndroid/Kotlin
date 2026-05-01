package cz.stokratandroid.inzertniid

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val executor: Executor = Executors.newSingleThreadExecutor()

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

    fun onClick(view: View?) {
        getAAID()
    }

    fun getAAID() {
        executor.execute {
            val txtAdvertisingId = findViewById<View>(R.id.txtAdvertisingId) as TextView
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(this@MainActivity)
                val appId = if (adInfo != null) adInfo.id else "Advertising ID nelze načíst."
                txtAdvertisingId.text = appId
            } catch (e: Exception) {
                txtAdvertisingId.text = "chyba"
            }
        }
    }
}