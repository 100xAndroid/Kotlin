package cz.stokratandroid.sharedpreferences

import android.os.Bundle
import android.view.View
import android.widget.EditText
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

        nacistData(null)
    }

    fun ulozitData(sender: View?) {
        // nacist hodnoty z formulare
        val editText1 = findViewById<View>(R.id.editText1) as EditText
        val inputText = editText1.text.toString()

        val editText2 = findViewById<View>(R.id.editText2) as EditText
        val text2 = editText2.text.toString()
        val inputInt = text2.toInt()

        // otevrit soubor pro zapis preferenci
        val sharedPreferences = getPreferences(MODE_PRIVATE)

        // vytvorit objekt editor preferenci
        val editor = sharedPreferences.edit()

        // ulozit do editoru klic a hodnotu z formulare
        editor.putString("text1", inputText)
        editor.putInt("text2", inputInt)

        // ulozit data
        editor.apply()
    }

    fun nacistData(sender: View?) {
        // otevrit soubor pro cteni preferenci
        val sharedPreferences = getPreferences(MODE_PRIVATE)

        // nacist ulozenou hodnotu
        val text1 = sharedPreferences.getString("text1", "default")
        val text2 = sharedPreferences.getInt("text2", 0).toString()

        // ulozit hodnoty do formulare
        val editText1 = findViewById<View>(R.id.editText1) as EditText
        editText1.setText(text1)
        val editText2 = findViewById<View>(R.id.editText2) as EditText
        editText2.setText(text2)
    }
}
