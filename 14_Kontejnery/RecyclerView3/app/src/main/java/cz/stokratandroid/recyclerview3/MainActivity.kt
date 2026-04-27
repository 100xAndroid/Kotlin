package cz.stokratandroid.recyclerview3

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    internal var lstVerzeAndroidu = ArrayList<String>()
    internal var adapter = RecyclerAdapter(lstVerzeAndroidu)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        naplnitRecyclerView()
    }

    // Metoda nastavi hodnoty do RecyclerView recViewVerzeAndroidu
    private fun naplnitRecyclerView() {
        // vstupní data
        // vzdy dva texty oddelene dvojteckou, adapter si je rozdeli
        lstVerzeAndroidu.add("Android 4.0 - 4.0.4:Ice Cream Sandwich")
        lstVerzeAndroidu.add("Android 4.1 - 4.3.1:Jelly Bean")
        lstVerzeAndroidu.add("Android 4.4 - 4.4.4:KitKat")
        lstVerzeAndroidu.add("Android 5.0 - 5.1.1:Lollipop")
        lstVerzeAndroidu.add("Android 6.0 - 6.0.1:Marshmallow")
        lstVerzeAndroidu.add("Android 7.0 - 7.1.2:Nougat")
        lstVerzeAndroidu.add("Android 8.0 – 8.1:Oreo")
        lstVerzeAndroidu.add("Android 9.0:Pie")
        lstVerzeAndroidu.add("Android 10 a vyšší:Android 10")

        // vytvorime instanci kontejneru RecyclerView
        val recyclerView = findViewById<View>(R.id.recViewVerzeAndroidu) as RecyclerView

        // vytvorime a priradime Layout Manager, ktery nastavuje chovani komponenty RecyclerView
        // V tomto pripade chceme aby se chovala jako Linear Layout
        recyclerView.layoutManager = LinearLayoutManager(this)

        // nastaveni oddelovace radku
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // propojeni adapteru s kontejnerem RecyclerView
        recyclerView.adapter = adapter

        // zaregistrovat zpetne volani pri posunuti polozky a propojit ho s recyclerView
        val itemTouchHepler = ItemTouchHelper(itemTouchCallback)
        itemTouchHepler.attachToRecyclerView(recyclerView)
    }

    // zpetne volani pri posunuti polozky seznamu
    var itemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START or ItemTouchHelper.END) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                lstVerzeAndroidu.removeAt(viewHolder.adapterPosition)
                adapter.notifyDataSetChanged()
            }
        }
}
