package com.example.movieapp.ui.details.movie.behavior

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.google.android.material.appbar.MaterialToolbar

class ToolbarBehavior(
        context: Context,
        attributeSet: AttributeSet
):CoordinatorLayout.Behavior<MaterialToolbar>(context, attributeSet) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: MaterialToolbar, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    private var endPosition:Int = context.resources.getDimensionPixelSize(R.dimen.back_height)
    private var position:Int = 0

    private val endColor = getSurfaceColor(context)
    private val startColor = Color.TRANSPARENT

    private val textTitleColor = getTextColor(context)



    override fun onDependentViewChanged(parent: CoordinatorLayout, child: MaterialToolbar, dependency: View): Boolean {
        endPosition -= child.height
        (dependency as RecyclerView).addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                position += dy
                if (position <= 0) position = 0
                if (position >= endPosition) position = endPosition
                animateToolbar(child)
            }
        })
        return true
    }

    private fun animateToolbar(toolbar: MaterialToolbar){
        val progress = when{
            position == 0 -> 0f
            position >= endPosition -> 1f
            else -> position.toFloat() / endPosition
        }
        val p = keyProgress(0.4f, progress)
        updateToolbarBackgroundColor(toolbar, p)
        updateToolbarTitleColor(toolbar, p)
        updateToolbarElevation(toolbar, p)
    }

    private fun keyProgress(keyPosition:Float, progress: Float):Float{
        return when{
            progress <= keyPosition -> 0f
            progress >= 1f -> 1f
            else -> (progress - keyPosition) / (1f - keyPosition)
        }
    }

    private fun updateToolbarBackgroundColor(toolbar: MaterialToolbar, progress:Float){
        val bg = blendColor(startColor, endColor, progress)
        toolbar.setBackgroundColor(bg)
    }

    private fun updateToolbarTitleColor(toolbar: MaterialToolbar, progress:Float){
        val color = blendColor(startColor, textTitleColor, progress)
        toolbar.setTitleTextColor(color)
    }

    private fun updateToolbarElevation(toolbar: MaterialToolbar, progress: Float){
        val elevation = 4.dp
        toolbar.elevation = elevation * progress
    }


    private fun blendColor(color1:Int, color2:Int, ratio:Float):Int{
        return ColorUtils.blendARGB(color1, color2, ratio)
    }

    override fun onSaveInstanceState(parent: CoordinatorLayout, child: MaterialToolbar): Parcelable? {
        return bundleOf(POSITION to position)
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: MaterialToolbar, state: Parcelable) {
        super.onRestoreInstanceState(parent, child, state)
        val restore = (state as Bundle).getInt(POSITION, 0)
        position = restore
//        animateToolbar(child)
    }



    private fun getSurfaceColor(context: Context):Int{
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorSurface, typedValue, true)
        return typedValue.data
    }

    private fun getTextColor(context: Context):Int{
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.colorOnSecondary, typedValue, true)
        return typedValue.data
    }

    private val Int.dp
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    companion object{
        private const val POSITION = "com.example.movieapp.ui.movie.details.behavior"
    }
}