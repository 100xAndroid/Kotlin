package cz.stokratandroid.explicitniintent

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    // spusteni aktivity Activity2
    fun intent1(view: View?) {
        val intent = Intent(this, Activity2::class.java)
        startActivity(intent)
    }

    // predani parametru a spusteni aktivity Activity3
    fun intent2(view: View?) {
        val intent = Intent(this, Activity3::class.java)
        intent.putExtra("param1", "test")
        intent.putExtra("param2", 10)
        startActivity(intent)
    }
}