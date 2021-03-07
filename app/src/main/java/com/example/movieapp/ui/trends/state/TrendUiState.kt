package com.example.movieapp.ui.trends.state

import android.os.Parcelable
import androidx.paging.PagingData
import com.example.movieapp.domain.model.TrendsData
import com.example.movieapp.until.MediaTypes
import com.example.movieapp.until.TimeWindow
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow


sealed class TrendAction{
    object LostInternetConnection:TrendAction()
    object NoInternetConnection:TrendAction()
    data class FetchData(val parameters: TrendQueryParameters):TrendAction()
}

sealed class TrendEvent{
    data class ShowToast(val msg:String):TrendEvent()
}

sealed class State{
    object NoInternetConnection:State()
    object Loading:State()
    data class DataSuccess(
            val pagingData: Flow<PagingData<TrendsData>>? = null,
            val queryParameters: TrendQueryParameters
    ):State()
}


@Parcelize
data class TrendQueryParameters(
    val trendTimeWindow: TimeWindow,
    val trendContentType:MediaTypes
):Parcelable{

    companion object{
        fun createTimeWindowParameters(timeWindow: TimeWindow, oldParam:TrendQueryParameters?):TrendQueryParameters{
            return oldParam?.let { TrendQueryParameters(timeWindow, oldParam.trendContentType) }
                    ?: TrendQueryParameters(timeWindow, MediaTypes.All)
        }

        fun createContentTypeParameters(mediaTypes: MediaTypes, oldParam:TrendQueryParameters?):TrendQueryParameters{
            return oldParam?.let { TrendQueryParameters(oldParam.trendTimeWindow, mediaTypes) }
                    ?: TrendQueryParameters(TimeWindow.Day, mediaTypes)
        }
    }
}