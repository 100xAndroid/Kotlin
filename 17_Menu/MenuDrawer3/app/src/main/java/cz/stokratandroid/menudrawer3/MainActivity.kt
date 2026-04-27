package cz.stokratandroid.menudrawer3

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

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
        nastavitDrawer()

        // nastaveni tlacitka
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        nastavitListener()
    }

    private fun nastavitListener() {
        // nastaveni posluchace udalosti kliknuti do menu
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_70 -> {
                    // akce po volbe Androidu verze 7.0
                }
                R.id.nav_71 -> {
                    // akce po volbe Androidu verze 7.1
                }
                R.id.nav_80 -> {
                    // akce po volbe Androidu verze 8.0
                }
                R.id.nav_81 -> {
                    // akce po volbe Androidu verze 8.1
                }
                R.id.nav_90 -> {
                    // akce po volbe Androidu verze 9.0
                }
                R.id.nav_10 -> {
                    // akce po volbe Androidu verze 10+
                }
            }

            // zobrazi text na displeji
            Toast.makeText(
                this@MainActivity,
                "Zvolena položka " + menuItem.getTitle(),
                Toast.LENGTH_SHORT
            ).show()

            // zavrit drawer menu
            drawerLayout!!.closeDrawer(GravityCompat.START)
            true
        }
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
}
