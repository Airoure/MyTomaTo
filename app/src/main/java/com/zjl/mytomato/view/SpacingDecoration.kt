package com.zjl.mytomato.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zjl.mytomato.util.dp


class SpacingDecoration(
        val hSpacing: Float = 0f,
        val vSpacing: Float = 0f,
        val includeHEdge: Boolean = false,
        val includeVEdge: Boolean = false
) : RecyclerView.ItemDecoration() {
    private var mHorizontalSpacing = 0
    private var mVerticalSpacing = 0

    private var mHeaderSpacing = 0
    private var mFooterSpacing = 0

    init {
        mHorizontalSpacing = hSpacing.dp.toInt()
        mVerticalSpacing = vSpacing.dp.toInt()
    }

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        var position = parent.getChildAdapterPosition(view)
        when (parent.layoutManager) {
            is GridLayoutManager -> {
                val layoutManager: GridLayoutManager = parent.layoutManager as GridLayoutManager
                val spanCount: Int = layoutManager.spanCount
                val column = position % spanCount
                getGridItemOffsets(outRect, position, column, spanCount)
            }
            is StaggeredGridLayoutManager -> {
                val layoutManager: StaggeredGridLayoutManager =
                        parent.layoutManager as StaggeredGridLayoutManager
                val spanCount: Int = layoutManager.getSpanCount()
                val lp: StaggeredGridLayoutManager.LayoutParams =
                        view.layoutParams as StaggeredGridLayoutManager.LayoutParams
                val column: Int = lp.spanIndex
                getGridItemOffsets(outRect, position, column, spanCount)
            }
            is LinearLayoutManager -> {
                if (mHeaderSpacing > 0 && position == 0) {
                    outRect.left = mHeaderSpacing
                } else {
                    outRect.left = mHorizontalSpacing
                }
                val itemCount = parent.adapter?.itemCount ?: 0
                if (position <= itemCount - 1) {
                    outRect.right = if (mFooterSpacing > 0) mFooterSpacing else mHorizontalSpacing
                }
                if (includeVEdge) {
                    if (position == 0) {
                        outRect.top = mVerticalSpacing
                    }
                    outRect.bottom = mVerticalSpacing
                } else {
                    if (position > 0) {
                        outRect.top = mVerticalSpacing
                    }
                }
            }
        }
    }

    private fun getGridItemOffsets(outRect: Rect, position: Int, column: Int, spanCount: Int) {
        if (includeHEdge) {
            outRect.left = mHorizontalSpacing * (spanCount - column) / spanCount
            outRect.right = mHorizontalSpacing * (column + 1) / spanCount
        } else {
            outRect.left = mHorizontalSpacing * column / spanCount
            outRect.right = mHorizontalSpacing * (spanCount - 1 - column) / spanCount
        }
        if (includeVEdge) {
            if (position < spanCount) {
                outRect.top = mVerticalSpacing
            }
            outRect.bottom = mVerticalSpacing
        } else {
            if (position >= spanCount) {
                outRect.top = mVerticalSpacing
            }
        }
    }
}