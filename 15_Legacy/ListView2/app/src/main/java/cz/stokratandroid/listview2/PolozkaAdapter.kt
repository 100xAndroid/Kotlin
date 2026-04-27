package cz.stokratandroid.listview2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class PolozkaAdapter
    (context: Context?, androidVerze: Array<String>?) : ArrayAdapter<String>(
    context!!, R.layout.polozka, androidVerze!!
) {
    // predefinovani metody getView tak, aby vracela nami nadefinovanou polozku
    override fun getView(pozice: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        // kontrola jestli uz neni convertView naplneny
        if (convertView == null) {
            // prevedeni polozky na Viev
            convertView = LayoutInflater.from(context).inflate(R.layout.polozka, parent, false)
        }

        // ziskani retezce z pole, podle pozice
        val strItem = getItem(pozice)
        // rozdeleni retezce na casti
        val strVersion =
            strItem!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        // nalezeni jednotlivych elementu a prirazeni do promennych
        val imgLogo = convertView!!.findViewById<View>(R.id.imgLogo) as ImageView
        val txtName = convertView.findViewById<View>(R.id.txtName) as TextView
        val txtVersion = convertView.findViewById<View>(R.id.txtVersion) as TextView

        // zpristupneni resources
        val res = context.resources
        // jmeno resource s logem androidu
        val resName = strVersion[1].replace(" ".toRegex(), "").lowercase(Locale.getDefault())
        // id resource s logem androidu
        val imgLogoId = res.getIdentifier(resName, "drawable", context.packageName)
        // prirazeni loga do ImageView
        imgLogo.setImageResource(imgLogoId)
        // prirazeni nazvu a verze do textovych poli
        txtName.text = strVersion[1]
        txtVersion.text = strVersion[0]

        // upravene View odesleme jako navratovou hodnotu
        return convertView
    }
}