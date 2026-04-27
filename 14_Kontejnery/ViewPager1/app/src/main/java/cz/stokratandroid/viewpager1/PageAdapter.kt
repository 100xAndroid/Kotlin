package cz.stokratandroid.viewpager1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return StrankaNougat()
            1 -> return StrankaOreo()
            2 -> return StrankaPie()
        }
        return StrankaNougat()
    }

    override fun getItemCount(): Int {
        return 3
    }
}