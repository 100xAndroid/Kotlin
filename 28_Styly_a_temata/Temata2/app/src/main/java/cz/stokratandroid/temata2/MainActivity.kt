package cz.stokratandroid.temata2

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (iTema == 1) {
            setTheme(R.style.Theme_Tema1)
        } else if (iTema == 2) {
            setTheme(R.style.Theme_Tema2)
        } else {
            setTheme(R.style.Theme_Tema3)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onClickTema1(view: View?) {
        iTema = 1
        recreate()
    }

    fun onClickTema2(view: View?) {
        iTema = 2
        recreate()
    }

    fun onClickTema3(view: View?) {
        iTema = 3
        recreate()
    }

    companion object {
        private var iTema = 1
    }
}