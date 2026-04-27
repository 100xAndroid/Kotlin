package cz.stokratandroid.recyclerview5

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var txtName: TextView
    var txtVersion: TextView
    var imgLogo: ImageView

    init {

        // registrace udalosti kliknuti na polozku
        itemView.setOnClickListener(this)

        // nalezeni jednotlivych elementu a prirazeni do promennych
        txtName = itemView.findViewById(R.id.txtName)
        txtVersion = itemView.findViewById(R.id.txtVersion)
        imgLogo = itemView.findViewById(R.id.imgLogo)
    }

    override fun onClick(view: View) {
        Toast.makeText(view.context, txtName.text, Toast.LENGTH_SHORT).show()
    }
}
