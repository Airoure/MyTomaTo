package com.zjl.mytomato.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        when(parent.layoutManager){
            is LinearLayoutManager->{
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
            is GridLayoutManager->{

            }
        }
    }
}