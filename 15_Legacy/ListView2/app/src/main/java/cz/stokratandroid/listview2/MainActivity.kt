package cz.stokratandroid.listview2

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        naplnitListView()
    }

    // funkce nastavi hodnoty do ListView listViewVerzeAndroidu
    private fun naplnitListView() {
        // vstupní data
        // vzdy dva texty oddelene dvojteckou, adapter si je rozdeli
        val arrVerzeAndroidu = arrayOf(
            "Android 4.0 - 4.0.4:Ice Cream Sandwich",
            "Android 4.1 - 4.3.1:Jelly Bean",
            "Android 4.4 - 4.4.4:KitKat",
            "Android 5.0 - 5.1.1:Lollipop",
            "Android 6.0 - 6.0.1:Marshmallow",
            "Android 7.0 - 7.1.2:Nougat",
            "Android 8.0 – 8.1:Oreo",
            "Android 9.0:Pie",
            "Android 10 a vyšší:Android 10"
        )

        // vytvorime instanci adapteru
        val adapter: ListAdapter = PolozkaAdapter(this, arrVerzeAndroidu)
        // propojeni adapteru s kontejnerem ListView
        val listView = findViewById<View>(R.id.listViewVerzeAndroidu) as ListView
        listView.adapter = adapter

        // vytvoreni instance posluchace udalosti kliknuti na polozku
        val clickListener = OnItemClickListener { parent, view, pozice, id ->

            // zjisti text na vybrane polozce
            val textPolozky = parent.getItemAtPosition(pozice).toString()
            // zobrazi text na displeji

            Toast.makeText(this@MainActivity, textPolozky.replace(":", " - ")
                , Toast.LENGTH_SHORT).show()

        }

        // nastaveni posluchace udalosti
        listView.onItemClickListener = clickListener
    }
}
