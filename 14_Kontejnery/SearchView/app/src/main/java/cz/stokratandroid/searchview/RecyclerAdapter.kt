package cz.stokratandroid.searchview

import RecyclerHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class RecyclerAdapter(data: MutableList<String>) :
    RecyclerView.Adapter<RecyclerHolder>(), Filterable {

    private val androidVerze: MutableList<String> = data
    private val androidVerzeVse: MutableList<String> = ArrayList(data)

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

    override fun getFilter(): Filter {
        return androidVerzeFiltr
    }

    private val androidVerzeFiltr: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<String> = ArrayList()
            if (constraint.length == 0) {
                filteredList.addAll(androidVerzeVse)
            } else {
                val filtrovacíText =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in androidVerzeVse) {
                    if (item.lowercase(Locale.getDefault()).contains(filtrovacíText)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            androidVerze.clear()
            androidVerze.addAll(results.values as MutableList<String>)
            notifyDataSetChanged()
        }
    }

    init {
        androidVerzeVse.toMutableList().addAll(androidVerze)
    }

    override fun getItemCount(): Int {
        return androidVerze.size
    }
}
