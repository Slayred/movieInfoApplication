package com.chibisov.movieinfoapplication.adapter.itemDecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class CustomHorizontalItemDecoration(private val dividerDrawable: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }
        rect.right =
            if (position % 2 == 0 || position == 0) 20
            else 1 //dividerDrawable.intrinsicWidth
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.children
            .forEach { view ->
                val position = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                if (position % 2 == 0 || position == 0) {
                    val left = view.right
                    val top = view.top
                    val right = left + 10
                    val bottom = view.bottom
                    dividerDrawable.bounds = Rect(left, top, right, bottom)
                    dividerDrawable.draw(canvas)
                }
            }
    }
}