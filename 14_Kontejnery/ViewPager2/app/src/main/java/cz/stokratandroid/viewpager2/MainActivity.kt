package cz.stokratandroid.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // vstupní data
        // vzdy tri texty oddelene dvojteckou, adapter si je rozdeli
        val arrVerzeAndroidu = arrayOf(
            "Android 4.0:Ice Cream Sandwich:Aktualizace 4.0.1, 4.0.2, 4.0.3 a 4.0.4\nAPI verze 14, 15",
            "Android 4.1 - 4.3:Jelly Bean:Aktualizace 4.1.1, 4.1.2, 4.2.1, 4.2.2 a 4.3.1\nAPI verze 16-18",
            "Android 4.4:KitKat:Aktualizace 4.4.1, 4.4.2, 4.4.3, 4.4.4, 4.4W, 4.4W.1 a 4.4W.2\nAPI verze 19, 20",
            "Android 5.0:Lollipop:Aktualizace 5.0.1, 5.0.2 a 5.1.1\nAPI verze 21, 22",
            "Android 6.0:Marshmallow:Aktualizace 6.0.1\nAPI verze 23",
            "Android 7.0:Nougat:Aktualizace 7.1.1 a 7.1.2\nAPI verze 24, 25",
            "Android 8.0 – 8.1:Oreo:Bez aktualizací\nAPI verze 26.27",
            "Android 9.0:Pie:Bez aktualizací\nAPI verze 28",
            "Android 10:Android 10:Bez aktualizací\nAPI verze 29"
        )

        // nova instance adapteru
        val mAdapter = PageAdapter(this, arrVerzeAndroidu)

        // prirazeni adapteru k ViewPager
        val viewPager = findViewById<View>(R.id.viewPager) as ViewPager2
        viewPager.adapter = mAdapter

        // viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        // viewPager.setPageTransformer(MarginPageTransformer(500))

        // nastaveni transformace stranky
        viewPager.setPageTransformer(PageTransformer())
    }
}