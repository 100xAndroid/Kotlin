package cz.stokratandroid.recyclerview2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txtNazev: TextView
    var txtVerze: TextView
    var imgLogo: ImageView

    init {

        // nalezeni jednotlivych objektu formulare a prirazeni do promennych
        txtNazev = itemView.findViewById(R.id.txtName)
        txtVerze = itemView.findViewById(R.id.txtVersion)
        imgLogo = itemView.findViewById(R.id.imgLogo)
    }
}
