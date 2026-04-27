package cz.stokratandroid.recyclerview4

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class PresouvaniHelper(private val contract: PresunUkoncenUpozorneni) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        // val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        contract.polozkaPresunuta(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    // interface ktery slouzi k upozorneni adapteru, ze doslo ke zmene v umisteni polozek
    interface PresunUkoncenUpozorneni {
        fun polozkaPresunuta(oldPosition: Int, newPosition: Int)
        fun polozkaOdsunuta(position: Int)
    }
}
