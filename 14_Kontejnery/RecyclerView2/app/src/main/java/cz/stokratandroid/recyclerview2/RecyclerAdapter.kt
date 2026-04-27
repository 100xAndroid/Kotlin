package cz.stokratandroid.recyclerview2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

internal class RecyclerAdapter (private val androidVerzeData: List<String>, private val mainActivity: MainActivity) :
    RecyclerView.Adapter<RecyclerHolder>() {

    private lateinit var rowItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        rowItem = LayoutInflater.from(parent.context).inflate(R.layout.polozka, parent, false)
        rowItem.setOnClickListener(mItemClick)
        return RecyclerHolder(rowItem)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, pozice: Int) {

        // barva pozadi polozky
        holder.itemView.isSelected = holder.itemView.isSelected

        // ziskani retezce z pole, podle pozice
        val strPolozka = androidVerzeData[pozice]

        // rozdeleni retezce na casti
        val strVerze = strPolozka.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // zpristupneni resources
        val res = rowItem!!.resources

        // jmeno resource s logem androidu
        val resNazev = strVerze[1].replace(" ".toRegex(), "").lowercase(Locale.getDefault())

        // id resource s logem androidu
        val imgLogoId = res.getIdentifier(resNazev, "drawable", rowItem!!.context.packageName)

        // predani hodnot prostrednictvim holderu k zobrazeni
        holder.txtNazev.text = strVerze[1]
        holder.txtVerze.text = strVerze[0]
        holder.imgLogo.setImageResource(imgLogoId)
    }

    override fun getItemCount(): Int {
        return androidVerzeData.size
    }

    private val mItemClick = View.OnClickListener { v ->
        val txtPolozkaText = v.findViewById<View>(R.id.txtName) as TextView

        // zobrazeni toast zpravy
        // Toast.makeText(v.getContext(), txtPolozkaText.getText(), Toast.LENGTH_SHORT).show()

        // volani metody tridy MainActivity
        mainActivity.onClickCalled(txtPolozkaText.text.toString())
    }
}
