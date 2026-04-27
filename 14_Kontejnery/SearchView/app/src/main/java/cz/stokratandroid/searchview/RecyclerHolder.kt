import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cz.stokratandroid.searchview.R

class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var txtPolozkaText: TextView

    init {
        itemView.setOnClickListener(this)
        txtPolozkaText = itemView.findViewById(R.id.txtPolozkaText)
    }

    override fun onClick(view: View) {
        Toast.makeText(view.context, txtPolozkaText.text, Toast.LENGTH_SHORT).show()
    }
}