package cz.stokratandroid.menu3

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
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

        // zaregistrovat kontextove menu na obrazek
        val obrazek = findViewById<View>(R.id.obrazek) as ImageView
        registerForContextMenu(obrazek)

        // zaregistrovat kontextove menu na tlacitko
val button = findViewById<View>(R.id.tlacitko) as Button
registerForContextMenu(button)
    }

    // udalost vyvolavana pred otevirenim kontextoveho menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        if (v.id == R.id.obrazek) inflater.inflate(
            R.menu.menu_context1,
            menu
        ) else if (v.id == R.id.tlacitko) inflater.inflate(R.menu.menu_context2, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "Zvolena položka menu " + item.title, Toast.LENGTH_SHORT).show()
        return super.onContextItemSelected(item)
    }
}