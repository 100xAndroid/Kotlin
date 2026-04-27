package cz.stokratandroid.listview1


import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
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
        val arrVerzeAndroidu = arrayOf(
            "Android 4.0 - 4.0.4",
            "Android 4.1 - 4.3.1",
            "Android 4.4 - 4.4.4",
            "Android 5.0 - 5.1.1",
            "Android 6.0 - 6.0.1",
            "Android 7.0 - 7.1.2",
            "Android 8.0 - 8.1",
            "Android 9.0",
            "Android 10",
            "Android 11",
            "Android 12",
            "Android 13",
            "Android 14",
            "Android 15"
        )

        // vytvorit instanci adapteru
        val adapter: ListAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, arrVerzeAndroidu)

        // propojeni adapteru s kontejnerem ListView
        val listView = findViewById<View>(R.id.listViewVerzeAndroidu) as ListView
        listView.adapter = adapter

        // vytvoreni instance posluchace udalosti kliknuti na polozku
        val clickListener = OnItemClickListener { parent, view, pozice, id ->

            // zjistit text na vybrane polozce
            val textPolozky = parent.getItemAtPosition(pozice).toString()

            // zobrazit text na displeji
            Toast.makeText(this@MainActivity, textPolozky, Toast.LENGTH_SHORT).show()

        }

        // nastaveni posluchace udalosti
        listView.onItemClickListener = clickListener
    }
}
