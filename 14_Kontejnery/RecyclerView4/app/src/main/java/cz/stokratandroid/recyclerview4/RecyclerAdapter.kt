package cz.stokratandroid.recyclerview4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.stokratandroid.recyclerview4.PresouvaniHelper.PresunUkoncenUpozorneni
import java.util.Locale

internal class RecyclerAdapter // konstruktor adapteru
    (private val androidVerzeData: ArrayList<String>) : RecyclerView.Adapter<RecyclerHolder>(),
    PresunUkoncenUpozorneni {
    private lateinit var rowItem: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        rowItem = LayoutInflater.from(parent.context).inflate(R.layout.polozka, parent, false)
        return RecyclerHolder(rowItem)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, pozice: Int) {

        // ziskani retezce z pole, podle pozice
        val strItem = androidVerzeData[pozice]

        // rozdeleni retezce na casti
        val strVersion = strItem.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // zpristupneni resources
        val res = rowItem!!.resources
        // jmeno resource s logem androidu
        val resName = strVersion[1].replace(" ".toRegex(), "").lowercase(Locale.getDefault())
        // id resource s logem androidu
        val imgLogoId = res.getIdentifier(resName, "drawable", rowItem!!.context.packageName)

        // predani hodnot holderu k zobrazeni
        holder.txtName.text = strVersion[1]
        holder.txtVersion.text = strVersion[1]
        holder.imgLogo.setImageResource(imgLogoId)
    }

    override fun getItemCount(): Int {
        return androidVerzeData.size
    }

    override fun polozkaPresunuta(oldPosition: Int, newPosition: Int) {
        val presouvanaPolozka = androidVerzeData[oldPosition]
        androidVerzeData.removeAt(oldPosition)
        androidVerzeData.add(newPosition, presouvanaPolozka)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun polozkaOdsunuta(position: Int) {
        // androidVerzeData.remove(position);
        notifyItemRemoved(position)
    }
}
