package cz.stokratandroid.gridview1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        naplnitGridView()
    }

    // funkce nastavi hodnoty do GridView gridViewVerzeAndroidu
    private fun naplnitGridView() {
        // vstupní data
        // znak \n je pouzity kvuli zalomeni textu
        val arrVerzeAndroidu = arrayOf(
            "Android\n2.0 - 2.1",
            "Android\n2.2 - 2.2.3",
            "Android\n2.3 - 2.3.7",
            "Android\n3.0 - 3.2.6",
            "Android\n4.0 - 4.0.4",
            "Android\n4.1 - 4.3.1",
            "Android\n4.4 - 4.4.4",
            "Android\n5.0 - 5.1.1",
            "Android\n6.0 - 6.0.1",
            "Android\n7.0 - 7.1.2",
            "Android\n8.0 - 8.1",
            "Android\n9.0",
            "Android\n10",
            "Android\n11",
            "Android\n12",
            "Android\n13",
            "Android\n14",
            "Android\n15"
        )

        // vytvorit instanci adapteru
        val adapter: ListAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, arrVerzeAndroidu)

        // propojeni adapteru s kontejnerem GridView
        val gridView = findViewById<View>(R.id.gridViewVerzeAndroidu) as GridView
        gridView.adapter = adapter


        // vytvoreni instance posluchace udalosti kliknuti na polozku
        val clickListener = OnItemClickListener { parent, view, pozice, id ->
            // zjistit text na vybrane polozce
            val textPolozky = parent.getItemAtPosition(pozice).toString()
            // zobrazit text na displeji
            Toast.makeText(this@MainActivity, textPolozky, Toast.LENGTH_SHORT).show()
        }

        // nastaveni posluchace udalosti
        gridView.onItemClickListener = clickListener
    }
}
