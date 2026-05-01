package cz.stokratandroid.sqlite2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ZalozkaVerze()
            1 -> return ZalozkaVlozit()
            2 -> return ZalozkaUpravit()
            3 -> return ZalozkaSmazat()
        }
        return ZalozkaVerze()
    }

    override fun getItemCount(): Int {
        return 4
    }
}