package com.example.movieapp.ui.trends.state

import android.os.Parcelable
import androidx.paging.PagingData
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.until.MediaTypes
import com.example.movieapp.until.TimeWindow
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow


sealed class TrendAction{
    data class TrendTimeWindow(val trendTimeWindow: TimeWindow, val language:String):TrendAction()
    data class TrendContentType(val mediaTypes: MediaTypes, val language:String):TrendAction()
}

data class TrendUiState(
    val pagingData: Flow<PagingData<TrendsData>>? = null,
    val queryParameters: TrendQueryParameters
){

    companion object{
        fun initState():TrendUiState{
            return TrendUiState(
                    queryParameters = TrendQueryParameters(
                        trendTimeWindow = TimeWindow.Day,
                        trendContentType = MediaTypes.All
                    )
            )
        }
    }
}

@Parcelize
data class TrendQueryParameters(
    val trendTimeWindow: TimeWindow,
    val trendContentType:MediaTypes,
    val language:String = "en-us"
):Parcelable