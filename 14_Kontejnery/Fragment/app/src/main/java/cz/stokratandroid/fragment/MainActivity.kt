package cz.stokratandroid.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ziskat spravce pro praci s fragmenty
        val manager = supportFragmentManager

        // vytvorit instanci fragmentu Fragment1
        val f1 = Fragment1()
        // v transakci priradit fragment do prvniho constraint layoutu
        manager.beginTransaction()
            .replace(R.id.firstLayout, f1, f1.tag)
            .commit()

        // vytvorit instanci fragmentu Fragment2
        val f2 = Fragment2()
        // v transakci priradit fragment do druheho constraint layoutu
        manager.beginTransaction()
            .replace(R.id.secondLayout, f2, f2.tag)
            .commit()
    }
}