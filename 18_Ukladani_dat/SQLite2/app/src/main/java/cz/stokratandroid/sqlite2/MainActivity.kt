package cz.stokratandroid.sqlite2

import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    // proměnná s odkazem na fragment se seznamem verzi
    var fragmentZalozkaVerze: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // nova instance adapteru
        val mAdapter = TabAdapter(this)

        // najdit na formulari TabLayout a ViewPager
        val tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout
        val viewPager = findViewById<View>(R.id.viewPager) as ViewPager2

        // propojeni ViewPager s adapterem;
        viewPager.setAdapter(mAdapter)

        // nazvy zalozek
        val zalozky = arrayOf("Verze", "Vložit", "Upravit", "Smazat")

        // propojeni TabLayoutu s ViewPager
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab, position ->
            tab.setText(
                zalozky[position]
            )
        }.attach()
    }

    // nacist data z DB tabulky, vytvorit adapter a propojit ho s gridView
    fun nacistData() {

        // test, jestli uz existuje zalozka verzi
        if (fragmentZalozkaVerze == null) return

        // instance tridy DatabaseHandler
        val dbHelper = DatabaseHandler(fragmentZalozkaVerze!!.context)

        // pripojeni se k databazi
        dbHelper.pripojitDB()

        // nacist data z tabulky
        val data = dbHelper.nacistData()

        // vytvorit instanci adapteru
        val arrFrom = arrayOf("_id", "Verze", "Nazev")
        val arrTo = intArrayOf(R.id.polozka_ID, R.id.polozka_verze, R.id.polozka_nazev)
        var adapter: SimpleCursorAdapter? = null
        try {
            adapter = SimpleCursorAdapter(
                fragmentZalozkaVerze!!.context,
                R.layout.polozka_gridu,
                data,
                arrFrom,
                arrTo,
                0
            )
        } catch (ex: Exception) {
            Toast.makeText(
                fragmentZalozkaVerze!!.context,
                "Chyba: " + ex.message,
                Toast.LENGTH_LONG
            ).show()
        }

        // propojeni adapteru s kontejnerem GridView
        val gridView =
            fragmentZalozkaVerze!!.findViewById<View>(R.id.gridViewVerzeAndroidu) as GridView
        gridView.adapter = adapter

        // odpojeni se od databaze
        dbHelper.odpojitDB()
    }

    // udalost tlacitka Vlozit zaznam
    fun butVlozitZaznam(view: View?) {

        // instance tridy DatabaseHandler
        val dbHelper = DatabaseHandler(this)

        // pripojeni se k databazi
        dbHelper.pripojitDB()
        val txtVerze = findViewById<View>(R.id.textVlozitVerze) as TextView
        val txtNazev = findViewById<View>(R.id.textVlozitNazev) as TextView
        dbHelper.vlozitZaznam(txtNazev.text.toString(), txtVerze.text.toString())
        Toast.makeText(this, "Záznam byl zapsaný do databáze", Toast.LENGTH_SHORT).show()

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData()
        dbHelper.odpojitDB()
    }

    // udalost tlacitka Upravit zaznam
    fun butUpravitZaznam(view: View?) {

        // instance tridy DatabaseHandler
        val dbHelper = DatabaseHandler(this)

        // pripojeni se k databazi
        dbHelper.pripojitDB()
        val txtId = findViewById<View>(R.id.textUpravitId) as TextView
        val txtVerze = findViewById<View>(R.id.textUpravitVerze) as TextView
        val txtNazev = findViewById<View>(R.id.textUpravitNazev) as TextView
        dbHelper.upravitZaznam(
            txtId.text.toString(),
            txtNazev.text.toString(),
            txtVerze.text.toString()
        )
        Toast.makeText(this, "Záznam byl zapsaný do databáze", Toast.LENGTH_SHORT).show()

        //  val zalozkaVerze = view.findViewWithTag<Fragment>("test")

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData()
        dbHelper.odpojitDB()
    }

    // udalost tlacitka Smazat zaznam
    fun butSmazatZaznam(view: View?) {

        // instance tridy DatabaseHandler
        val dbHelper = DatabaseHandler(this)

        // pripojeni se k databazi
        dbHelper.pripojitDB()
        val txtId = findViewById<View>(R.id.textSmazatId) as TextView
        dbHelper.smazatZaznam(txtId.text.toString())
        Toast.makeText(this, "Záznam byl vymazaný z databáze", Toast.LENGTH_SHORT).show()

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData()
        dbHelper.odpojitDB()
    }

    // udalost tlacitka Smazat zaznamy
    fun butSmazatZaznamy(view: View?) {

        // instance tridy DatabaseHandler
        val dbHelper = DatabaseHandler(this)

        // pripojeni se k databazi
        dbHelper.pripojitDB()
        dbHelper.smazatData()
        Toast.makeText(this, "Záznamy byly vymazané z databáze", Toast.LENGTH_SHORT).show()

        // znovunacteni dat do gridu a odpojeni se od DB
        nacistData()
        dbHelper.odpojitDB()
    }
}