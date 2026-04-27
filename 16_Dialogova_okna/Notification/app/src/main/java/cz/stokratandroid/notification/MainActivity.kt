package cz.stokratandroid.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

        // vytvorit kanal oznameni
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifKanal =
                NotificationChannel("NTF01", "Notifikace", NotificationManager.IMPORTANCE_HIGH)
            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.createNotificationChannel(notifKanal)
        }
    }

    // vytvorit a zobrazit oznameni
    fun OznameniOtevrit_onclick(view: View?) {
        val builder = NotificationCompat.Builder(this, "NTF01")
        builder.setContentTitle("Toto je notifikace")
        builder.setContentText("Textový popis notifikace.")
        builder.setSmallIcon(R.drawable.tlapka)
        builder.setAutoCancel(true)
        val manag = NotificationManagerCompat.from(this)
        manag.notify(1, builder.build())
    }

    // zavrit oznameni
    fun OznameniZavrit_onclick(view: View?) {
        val manag = NotificationManagerCompat.from(this)
        manag.cancel(1)
    }
}