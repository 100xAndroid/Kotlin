package cz.stokratandroid.expandablelistview


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class PolozkaAdapter(
    private val context: Context,
    private val skupiny: ArrayList<String>,
    itemList: ArrayList<Any>
) : BaseExpandableListAdapter() {
    private var aktualniPolozka: ArrayList<String>? = null
    private var polozky = ArrayList<Any>()

    init {
        polozky = itemList
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return polozky[groupPosition] as ArrayList<String>
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        customView: View?,
        parent: ViewGroup
    ): View {
        var customView = customView
        aktualniPolozka = polozky[groupPosition] as ArrayList<String>

        // pokud dosud nebyl formular serializovan, serializovat
        if (customView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            customView = inflater.inflate(R.layout.polozka, null)
        }

        // ziskat ze seznamu nazev
        val itemText = aktualniPolozka!![childPosition]

        // nazev zapsat do textoveho pole
        val textField = customView!!.findViewById<View>(R.id.textView1) as TextView
        textField.text = itemText

        // vratit vytvorenou polozku
        return customView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return (polozky[groupPosition] as ArrayList<String?>).size
    }

    override fun getGroup(groupPosition: Int): Any {
        return skupiny.get(groupPosition)
    }

    override fun getGroupCount(): Int {
        return skupiny.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {

        // pokud dosud nebyl formular serializovan, serializovat
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.skupina, null)
        }

        // ziskat ze seznamu nazev skupiny
        val textNazvuSkupiny = skupiny[groupPosition]

        // nazev zapsat do textoveho pole
        val nazevSkupiny = convertView!!.findViewById<View>(R.id.textView1) as TextView
        nazevSkupiny.text = textNazvuSkupiny

        // vratit vytvorene zahlavi skupiny
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}