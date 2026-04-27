package cz.stokratandroid.recyclerview1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val androidVerze: List<String>) :
    RecyclerView.Adapter<RecyclerHolder>() {
        
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val rowItem = LayoutInflater.from(parent.context).inflate(R.layout.polozka, parent, false)
        return RecyclerHolder(rowItem)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        // barva pozadi polozky
        holder.itemView.isSelected = holder.itemView.isSelected
        // text polozky
        holder.txtPolozkaText.text = androidVerze[position]
    }

    override fun getItemCount(): Int {
        return androidVerze.size
    }
}