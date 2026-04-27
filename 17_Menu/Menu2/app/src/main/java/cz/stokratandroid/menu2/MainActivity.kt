package cz.stokratandroid.menu2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
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

    // metoda volana kliknutim na tlacitko
    fun otevritPopUpMenu(v: View?) {
        // vytvorit menu
        val popup = PopupMenu(this, v!!)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_popup, popup.menu)

        // kliknuti na polozku menu
        popup.setOnMenuItemClickListener { item ->
            zvolenaPolozkaMenu(item)
            true
        }

        // zobrazit menu
        popup.show()
    }

    // metoda volana po kliknuti na polozku menu
    private fun zvolenaPolozkaMenu(item: MenuItem) {
        Toast.makeText(this, "Zvolena položka menu " + item.title, Toast.LENGTH_SHORT).show()
    }
}