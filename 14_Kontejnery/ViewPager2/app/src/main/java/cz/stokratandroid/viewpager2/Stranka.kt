package cz.stokratandroid.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [Stranka.newInstance] factory method to
 * create an instance of this fragment.
 */
class Stranka : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stranka, container, false)

        // nacteni a prirazeni vstupnich dat do pomocnych promennych
        val strLogo = arguments!!.getString("logo")
        val strNazev = arguments!!.getString("nazev")
        val strVerze = arguments!!.getString("verze")
        val strData = arguments!!.getString("data")

        // odkazy na objekty formulare
        val imgLogo = view.findViewById<ImageView>(R.id.imageView)
        val txtNazev = view.findViewById<TextView>(R.id.textView1)
        val txtVerze = view.findViewById<TextView>(R.id.textView2)
        val txtData = view.findViewById<TextView>(R.id.textView3)

        // zpristupneni resources
        val res = context!!.resources

        // id resource s logem androidu
        val imgLogoId = res.getIdentifier(strLogo, "drawable", context!!.packageName)

        // prirazeni loga do ImageView
        imgLogo.setImageResource(imgLogoId)

        // zapis textovych vstupnich dat na formular
        txtNazev.text = strNazev
        txtVerze.text = strVerze
        txtData.text = strData
        return view
    }
}