package cz.stokratandroid.viewpager2

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class PageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {

        // transformace 3d rotace frame
        view.rotationY = -position * 35f

        /*
        // jiny typ transformace - zmensovani mizejiciho frame
        val normalizedPosition = kotlin.math.abs(kotlin.math.abs(position) - 1)
        view.scaleX = normalizedPosition / 2 + 0.5f
        view.scaleY = normalizedPosition / 2 + 0.5f
        */
    }
}