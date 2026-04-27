package cz.stokratandroid.viewpager1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // nova instance adapteru
        val mAdapter = PageAdapter(this)

        // prirazeni adapteru k ViewPager
        val viewPager = findViewById<View>(R.id.viewPager) as ViewPager2
        viewPager.adapter = mAdapter
    }
}
