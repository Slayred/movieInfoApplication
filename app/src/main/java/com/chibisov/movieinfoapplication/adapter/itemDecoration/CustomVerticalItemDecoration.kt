package com.chibisov.movieinfoapplication.adapter.itemDecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CustomVerticalItemDecoration(private val dividerDrawable: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }
        rect.bottom =
            if (position % 2 == 0 || (position == (parent.adapter?.itemCount ?: 1) - 1)) 2
            else 10 //dividerDrawable.intrinsicWidth
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children
            .forEach { view ->
                val position = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                if (position % 2 != 0 && (position != (parent.adapter?.itemCount ?: 1) - 1)) {
                    val left = view.left
                    val top = view.bottom
                    val right = view.right
                    val bottom = top + 10
                    dividerDrawable.bounds = Rect(left, top, right, bottom)
                    dividerDrawable.draw(canvas)
                }
            }
    }
}