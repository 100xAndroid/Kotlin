package cz.stokratandroid.recyclerview5

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


abstract class PosouvaniHelper(context: Companion) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var recyclerView: RecyclerView? = null
    private var gestureDetector: GestureDetector? = null

    private var odsunutaPolozka = -1
    private var minuleOdsunutaPolozka = -1

    var tlacitko1: NakreslitTlacitko? = null
    var tlacitko2: NakreslitTlacitko? = null

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        minuleOdsunutaPolozka = odsunutaPolozka
        odsunutaPolozka = viewHolder.adapterPosition
        if (odsunutaPolozka == minuleOdsunutaPolozka) minuleOdsunutaPolozka = -1
        vratitOdsunutouPolozku()
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        // hodnota, o kolik musi uzivatel polozku posunout, aby zustala posunuta
        return 200f
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        // minimaleni rychlost posouvani
        return 0.1f * defaultValue
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        // rychlost, kterou musi uzivatel polozku posunout, aby zustala posunuta
        return 5.0f * defaultValue
    }

    // private int direction = 0;
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val odsouvanaPolozka = viewHolder.adapterPosition
        if (odsouvanaPolozka < 0) {
            odsunutaPolozka = -1
            return
        }
        Log.d("myTag", String.format("dx: %f", dX))
        // Log.d("myTag", String.format("dx: %f, direction: %d", dX, direction));
        var posunX = dX
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX <= 0) {  // posouvani doleva
            tlacitko1 = nakreslitTlacitko1(viewHolder)
            tlacitko2 = nakreslitTlacitko2(viewHolder)
            val itemView = viewHolder.itemView
            posunX = dX * 2 * BUTTON_WIDTH / itemView.width
           // posunX = -2f * BUTTON_WIDTH
            kreslitTlacitka(c, itemView, tlacitko1, tlacitko2, odsouvanaPolozka, posunX)
        }
        super.onChildDraw(c, recyclerView, viewHolder, posunX, dY, actionState, isCurrentlyActive)
    }

    private fun kreslitTlacitka(
        c: Canvas,
        itemView: View,
        tl1: NakreslitTlacitko?,
        tl2: NakreslitTlacitko?,
        pos: Int,
        posunX: Float
    ) {
        /*
        // roztahovani tlacitek
        val dButtonWidth: Float = (-1) * posunX / 2
        tl1.kreslitTlacitko(
            c,
            RectF(
                itemView.right - dButtonWidth,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            ),
            pos
        )
        tl2.kreslitTlacitko(
            c,
            RectF(
                itemView.right - dButtonWidth - dButtonWidth,
                itemView.top.toFloat(),
                itemView.right - dButtonWidth,
                itemView.bottom.toFloat()
            ),
            pos
        )
        */

        // posouvani tlacitek spolu s polozkou
        val hranaPolozky = itemView.right + posunX
        tl1!!.kreslitTlacitko(
            c,
            RectF(
                hranaPolozky,
                itemView.top.toFloat(),
                hranaPolozky + 200,
                itemView.bottom.toFloat()
            ),
            pos
        )
        tl2!!.kreslitTlacitko(
            c,
            RectF(
                hranaPolozky + 200,
                itemView.top.toFloat(),
                hranaPolozky + 400,
                itemView.bottom.toFloat()
            ),
            pos
        )
    }

    private val gestureListener: SimpleOnGestureListener = object : SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            tlacitko1!!.onClick(e.x, e.y)
            tlacitko2!!.onClick(e.x, e.y)
            return true
        }
    }

    private val onTouchListener = OnTouchListener { view, e ->
        if (odsunutaPolozka < 0) return@OnTouchListener false

        // zjistit o kterou polozku jde
        val swipedViewHolder = recyclerView!!.findViewHolderForAdapterPosition(odsunutaPolozka)
        val swipedItem = swipedViewHolder!!.itemView

        // udelat nad polozkou pomyslny obdelnik
        val rect = Rect()
        swipedItem.getGlobalVisibleRect(rect)

        // test, jestli jde o posun a jestli je posun provadeny stale nad polozkou.
        if (e.action == MotionEvent.ACTION_DOWN || e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_MOVE) {
            val point = Point(e.rawX.toInt(), e.rawY.toInt())
            if (rect.top < point.y && rect.bottom > point.y)
                gestureDetector?.onTouchEvent(e)
            else {
                minuleOdsunutaPolozka = odsunutaPolozka
                odsunutaPolozka = -1
                vratitOdsunutouPolozku()
            }
        }
        false
    }

    init {
        gestureDetector = GestureDetector(null, gestureListener)
    }

    private fun vratitOdsunutouPolozku() {
        if (minuleOdsunutaPolozka > -1) {
            recyclerView!!.adapter!!.notifyItemChanged(minuleOdsunutaPolozka)
            minuleOdsunutaPolozka = -1
        }
    }

    fun pripojitRecyclerView(recyclerView: RecyclerView?) {
        this.recyclerView = recyclerView
        this.recyclerView!!.setOnTouchListener(onTouchListener)
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(this.recyclerView)
    }

    // public abstract void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons);
    abstract fun nakreslitTlacitko1(viewHolder: RecyclerView.ViewHolder?): NakreslitTlacitko?
    abstract fun nakreslitTlacitko2(viewHolder: RecyclerView.ViewHolder?): NakreslitTlacitko?
    interface NakreslitTlacitkoClickListener {
        fun onClick(pos: Int)
    }

    class NakreslitTlacitko(
        private val textTlacitka: String,
        private val barvaTlacitka: Int,
        private val clickListener: NakreslitTlacitkoClickListener
    ) {
        private var pozice = 0
        private var clickRegion: RectF? = null

        fun onClick(x: Float, y: Float): Boolean {
            if (clickRegion != null && clickRegion!!.contains(x, y)) {
                clickListener.onClick(pozice)
                return true
            }
            return false
        }

        // vykresleni tlacitka
        fun kreslitTlacitko(c: Canvas, umisteni: RectF, pozice: Int) {
            val p = Paint()

            // vykresleni pozadi tlacitka
            p.color = barvaTlacitka
            c.drawRect(umisteni, p)

            // vykresleni textu tlacitka
            p.color = Color.WHITE
            p.textSize = Resources.getSystem().displayMetrics.density * 12
            val r = Rect()
            p.textAlign = Paint.Align.LEFT
            p.getTextBounds(textTlacitka, 0, textTlacitka.length, r)
            val x = umisteni.width() / 2f - r.width() / 2f
            val y = umisteni.height() / 2f + r.height() / 2f
            c.drawText(textTlacitka, umisteni.left + x, umisteni.top + y, p)
            clickRegion = umisteni
            this.pozice = pozice
        }
    }

    companion object {
        const val BUTTON_WIDTH = 200
    }
}