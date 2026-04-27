package cz.stokratandroid.prvnaplikace

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun kliknutiNaTlacitko(sender: View) {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setCancelable(true)
        builder.setTitle("Poznámka")
        builder.setMessage("Android je zde!")
        builder.setPositiveButton("Zavřít", null)
        builder.create().show()

        return
    }
}