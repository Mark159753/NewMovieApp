package com.example.movieapp.binding

import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.movieapp.R
import com.example.movieapp.data.model.movieDetails.Genre
import com.example.movieapp.data.model.movieDetails.ProductionCountry
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

@BindingAdapter("setAppName")
fun setAppName(view:TextView, appName:String){
    val span = SpannableString(appName)
    span.setSpan(ForegroundColorSpan(view.context.getColor(R.color.light_red)), 5, 7, 0)
    view.text = span
}

@BindingAdapter("loadImg")
fun loadImg(view:ImageView, url:String?){
    url?.let{
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500${it}")
            .into(view)
    }
}

@BindingAdapter("producedCounters")
fun producedCounters(view:TextView, list:List<ProductionCountry>?){
    list?.let { countryList->
        val res = StringBuilder()
        countryList.forEach {
            res.append(it.name + " ")
        }
        view.text = res.toString()
    }
}

@BindingAdapter( "realiseDate", "setGenres", requireAll = true)
fun yearPlusGenre(view:TextView, realiseDate:String?, genreList:List<Genre>?){
    if (realiseDate != null && genreList != null) {
        val year = realiseDate.substring(0, 4)
        val genre = StringBuilder()
        genreList.forEach {
            genre.append(it.name + " ")
        }
        genre.toString()
        view.text = "$year-$genre"
    }
}

@BindingAdapter("movieDuration")
fun setMovieDuration(view:TextView, runtime:Int?){
    runtime?.let {
        val h:Int = it / 60
        val min:Int = it % 60
        val res = "$h hr $min min"
        view.text = res
    }
}

@BindingAdapter("voteAverage")
fun setVoteAverage(view: RatingBar, voteAverage:Double?){
    voteAverage?.let {
        view.rating = it.toFloat()
    }
}