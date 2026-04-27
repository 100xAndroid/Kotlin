package cz.stokratandroid.tabhost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TabHost

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // najdeme kontejner TabHost
        // funkci setup je nutne zavolat pred vkladanim zalozek
        val tabHost = findViewById<View>(R.id.tabHost) as TabHost
        tabHost.setup()

        // 1. zalozka (Android)
        var tabSpec = tabHost.newTabSpec("Tab1")
        tabSpec.setIndicator("Android")
        tabSpec.setContent(R.id.tab1)
        tabHost.addTab(tabSpec)

        // 2. zalozka (Verze)
        tabSpec = tabHost.newTabSpec("Tab2")
        tabSpec.setIndicator("Verze")
        tabSpec.setContent(R.id.tab2)
        tabHost.addTab(tabSpec)

        // 3. zalozka (API)
        tabSpec = tabHost.newTabSpec("Tab3")
        tabSpec.setIndicator("API")
        tabSpec.setContent(R.id.tab3)
        tabHost.addTab(tabSpec)
    }
}