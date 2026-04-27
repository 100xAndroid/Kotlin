package cz.stokratandroid.logovani

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun log1(sender: View?) {
        // logovani urovne DEBUG
        Log.d("MainActivity", "Test log DEBUG")
    }

    fun log2(sender: View?) {

        // logovani urovne ERROR
        Log.e("MainActivity", "Test log ERROR")
    }

    fun log3(sender: View?) {
        // logovani urovne INFO
        Log.i("MainActivity", "Test log INFO")
    }

    fun stackTrace(sender: View?) {
        try {
            // zavolat metodu, ktera vyvola vyjimku
            vyvolatVyjimku()
        } catch (ex: Exception) {
            // vypsat stacktrace
            ex.printStackTrace()
        }
    }

    // Metoda umele zpusobi chybu
    private fun vyvolatVyjimku() {
        // vyvolat vyjimku
        throw Exception("Testovací výjimka")
    }
}