package cz.stokratandroid.menudrawer2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var drawerToggle: ActionBarDrawerToggle? = null
    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // nastaveni Toolbaru
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        naplnitRecyclerView()
        nastavitDrawer()

        // nastaveni tlacitka
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    private fun nastavitDrawer() {
        drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.drawer_otevrit,
            R.string.drawer_zavrit
        ) {
            // tato udalost se zavola jakmile se menu otevre
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                supportActionBar!!.title = "Verze Androidu"
            }

            // tato udalost se zavola jakmile se menu zavre
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                supportActionBar!!.title = title.toString()
            }
        }

        // nastaveni prepinace a udalosti
        drawerToggle?.setDrawerIndicatorEnabled(true)
        drawerLayout!!.addDrawerListener(drawerToggle!!)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle!!.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle!!.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun naplnitRecyclerView() {
        val lstVerzeAndroidu = ArrayList<String>()
        lstVerzeAndroidu.add("Android 4.0 - 4.0.4")
        lstVerzeAndroidu.add("Android 4.1 - 4.3.1")
        lstVerzeAndroidu.add("Android 4.4 - 4.4.4")
        lstVerzeAndroidu.add("Android 5.0 - 5.1.1")
        lstVerzeAndroidu.add("Android 6.0 - 6.0.1")
        lstVerzeAndroidu.add("Android 7.0 - 7.1.2")
        lstVerzeAndroidu.add("Android 8.0 - 8.1")
        lstVerzeAndroidu.add("Android 9.0")
        lstVerzeAndroidu.add("Android 10")
        lstVerzeAndroidu.add("Android 11")
        lstVerzeAndroidu.add("Android 12")
        lstVerzeAndroidu.add("Android 13")
        lstVerzeAndroidu.add("Android 14")
        lstVerzeAndroidu.add("Android 15")

        // vytvorime instanci adapteru
        val adapter = RecyclerAdapter(lstVerzeAndroidu)

        // najdeme na formulari kontejner RecyclerView
        val recyclerView = findViewById<View>(R.id.navList) as RecyclerView

        // vytvorime a priradime Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme, aby se chovala jako Linear Layout
        recyclerView.setLayoutManager(LinearLayoutManager(this))

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.setAdapter(adapter)
    }
}
