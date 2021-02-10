package com.example.movieapp.binding

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.movieapp.R

@BindingAdapter("setAppName")
fun setAppName(view:TextView, appName:String){
    val span = SpannableString(appName)
    span.setSpan(ForegroundColorSpan(view.context.getColor(R.color.light_red)), 5, 7, 0)
    view.text = span
}