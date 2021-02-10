package com.example.movieapp.until

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorator(
        private val verticalSpace:Int,
        private val horizontalSpace:Int,
        private val horizontal:Boolean = false
):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        with(outRect){
            if (horizontal){
                if (parent.getChildAdapterPosition(view) == 0){
                    left = horizontalSpace
                }
                top = verticalSpace
                bottom = verticalSpace
                right = horizontalSpace
            }else{
                if (parent.getChildAdapterPosition(view) == 0){
                    top = verticalSpace
                }
                bottom = verticalSpace
                right = horizontalSpace
                left = horizontalSpace
            }
        }
    }

}