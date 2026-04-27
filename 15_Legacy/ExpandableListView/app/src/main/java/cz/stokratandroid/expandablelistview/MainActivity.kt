package cz.stokratandroid.expandablelistview

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var expandbleList: ExpandableListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // nacist data do komponenty ExpandableListView
        setGroupData()
        setpolozkaGroupData()

        // ziskat odkaz na ExpandableListView
        expandbleList = findViewById<View>(R.id.expListViewNastaveniAndroidu) as ExpandableListView
        // zrusit oddelovac polozek, pouzijeme vlastni
        expandbleList!!.dividerHeight = 0

        // vytvorit adapter a predat mu vstupni data
        val mNewAdapter = PolozkaAdapter(this@MainActivity, skupiny, polozky)
        expandbleList!!.setAdapter(mNewAdapter)

        // listener kliknuti na polozku
        expandbleList!!.setOnChildClickListener { parent, v, groupPosition, polozkaPosition, id ->
            val aktualniPolozka = polozky[groupPosition] as ArrayList<String>
            Toast.makeText(
                baseContext,
                skupiny[groupPosition] + " " + aktualniPolozka[polozkaPosition],
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }

    // list se seznamem skupin
    var skupiny = ArrayList<String>()
    fun setGroupData() {
        skupiny.add("Ice Cream Sandwich")
        skupiny.add("Jelly Bean")
        skupiny.add("KitKat")
        skupiny.add("Lollipop")
        skupiny.add("Marshmallow")
        skupiny.add("Nougat")
        skupiny.add("Oreo")
        skupiny.add("Pie")
        skupiny.add("10")
        skupiny.add("11")
        skupiny.add("12")
        skupiny.add("13")
        skupiny.add("14")
        skupiny.add("15")
    }

    // list se seznamem objektu, kde kazdym objektem je seznam polozek
    var polozky = ArrayList<Any>()
    fun setpolozkaGroupData() {

        // Ice Cream Sandvwich
        var polozka = ArrayList<String?>()
        polozka.add("4.0")
        polozka.add("4.0.1")
        polozka.add("4.0.2")
        polozky.add(polozka)

        // Jelly Bean
        polozka = ArrayList()
        polozka.add("4.1")
        polozka.add("4.1.1")
        polozka.add("4.1.2")
        polozka.add("4.2.1")
        polozka.add("4.2.1")
        polozka.add("4.2.2")
        polozka.add("4.3")
        polozka.add("4.3.1")
        polozky.add(polozka)

        // KitKat
        polozka = ArrayList()
        polozka.add("4.4")
        polozka.add("4.4.1")
        polozka.add("4.4.2")
        polozka.add("4.4.3")
        polozka.add("4.4.4")
        polozky.add(polozka)

        // Lollipop
        polozka = ArrayList()
        polozka.add("5.0")
        polozka.add("5.0.1")
        polozka.add("5.0.2")
        polozka.add("5.1")
        polozka.add("5.1.1")
        polozky.add(polozka)

        // Marshmallow
        polozka = ArrayList()
        polozka.add("6.0")
        polozka.add("6.0.1")
        polozky.add(polozka)

        // Nougat
        polozka = ArrayList()
        polozka.add("7.0")
        polozka.add("7.1")
        polozka.add("7.1.1")
        polozka.add("7.1.2")
        polozky.add(polozka)

        // Oreo
        polozka = ArrayList()
        polozka.add("8.0")
        polozka.add("8.1")
        polozky.add(polozka)

        // Pie
        polozka = ArrayList()
        polozka.add("9.0")
        polozky.add(polozka)

        // 10
        polozka = ArrayList()
        polozka.add("10")
        polozky.add(polozka)

        // 11
        polozka = ArrayList()
        polozka.add("11")
        polozky.add(polozka)

        // 12
        polozka = ArrayList()
        polozka.add("12")
        polozky.add(polozka)

        // 13
        polozka = ArrayList()
        polozka.add("13")
        polozky.add(polozka)

        // 14
        polozka = ArrayList()
        polozka.add("14")
        polozky.add(polozka)

        // 15
        polozka = ArrayList()
        polozka.add("15")
        polozky.add(polozka)
    }
}
