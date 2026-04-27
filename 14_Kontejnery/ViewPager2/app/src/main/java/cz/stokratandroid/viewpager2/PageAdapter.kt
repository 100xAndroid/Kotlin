package cz.stokratandroid.viewpager2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.Locale

class PageAdapter(fragmentActivity: FragmentActivity, private val data: Array<String>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = Stranka()

        // ziskani retezce z pole, podle pozice
        val strItem = data[position]

        // rozdeleni retezce na casti
        val strVersion = strItem.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // jmeno resource s logem Androidu
        val resName = strVersion[1].replace(" ".toRegex(), "").lowercase(Locale.getDefault())

        // odeslani dat do fragmentu
        val bundle = Bundle()
        bundle.putString("logo", resName)
        bundle.putString("nazev", strVersion[1])
        bundle.putString("verze", strVersion[0])
        bundle.putString("data", strVersion[2])
        fragment.arguments = bundle
        return fragment
    }

    override fun getItemCount(): Int {
        return data.size
    }
}