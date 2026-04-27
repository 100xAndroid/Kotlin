package cz.stokratandroid.searchview

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    // promenna s ukazatelem na adapter
    var adapter: RecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        naplnitRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // pridat do Menu box pro hledani
        val inflanter = menuInflater
        inflanter.inflate(R.menu.menu_search, menu)
        val item = menu.findItem(R.id.menuSearch)
        val searchView = item.actionView as SearchView?

        // posluchac udalosti zmeny hodnoty v poli pro hledani
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // nastaveni filtru adapteru
                adapter!!.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    // metoda nastavi hodnoty do RecycelrView recViewVerzeAndroidu
    private fun naplnitRecyclerView() {

        // nacist data z resources
        val lstVerzeAndroidu = ArrayList<String>()
        lstVerzeAndroidu.addAll(Arrays.asList(*resources.getStringArray(R.array.androidVerze)))

        // vytvorit instanci adapteru
        adapter = RecyclerAdapter(lstVerzeAndroidu)

        // najit na formulari kontejner RecyclerView
        val recyclerView = findViewById<View>(R.id.recViewVerzeAndroidu) as RecyclerView

        // vytvorit a priradit Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme aby se chovala jako Linear Layout
        recyclerView.layoutManager = LinearLayoutManager(this)

        // nastavit oddelovace radku
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // nastavit pevnou velikost recyclerView (nemenit ji podle poctu polozek)
        recyclerView.setHasFixedSize(true)

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.adapter = adapter
    }
}