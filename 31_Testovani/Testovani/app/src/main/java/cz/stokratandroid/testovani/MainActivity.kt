package cz.stokratandroid.testovani

import android.os.Build
import android.os.Bundle
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
     companion object {

         fun testovaciFunkce(param1: Int, param2: Int): Int {
            val verze = Build.VERSION.SDK_INT
            val soucet = param1 + param2
            return soucet
        }

        fun testovaciFunkce2(): Int {
            val verze = Build.VERSION.SDK_INT
            return verze
        }

    }
}