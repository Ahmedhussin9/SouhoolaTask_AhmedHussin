package com.souhoolatask_ahmedhussin.util

import android.util.Log
import androidx.paging.PagingConfig
import com.elegance_oud.util.state.ApiState
import com.elegance_oud.util.state.UiText
import com.google.gson.Gson
import com.souhoolatask_ahmedhussin.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val PAGING_START_INDEX = 1
const val PAGING_PER_PAGE = 10

fun pagingConfig() = PagingConfig(pageSize = PAGING_PER_PAGE, enablePlaceholders = false)

fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {
    emit(ApiState.Loading())
    try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiState.Success(it))
                Log.e("networkResponse", "Success\n${it.toString()}")
            } ?: run {
                emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))
                Log.e("networkResponse", "Failure\n Response body is null")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            val errorObject = Gson().fromJson(errorBody, ResultModel::class.java)
            emit(ApiState.Error(UiText.DynamicString(errorObject.message)))
            Log.e("networkResponse", "Failure\n$errorBody")
        }

    } catch (e: HttpException) {
        Log.e("networkResponse", "HttpException\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))

    } catch (e: IOException) {
        Log.e("networkResponse", "IOException\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.check_your_internet_connection)))

    } catch (e: Exception) {
        Log.e("networkResponse", "Exception\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))
    }
}


fun <T> toResultFlowTemp(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {
    emit(ApiState.Loading())
    try {
        val response = call()
        if (response.code() == 200) {
            emit(ApiState.Success(response.body()))
            Log.e("networkResponse", "Success\n" + response.body().toString())

        } else {
            val errorBody = response.errorBody()?.string()
            val errorObject = Gson().fromJson(errorBody, ResultModel::class.java)
            emit(ApiState.Error(UiText.DynamicString(errorBody ?: "Something went wrong!")))
            Log.e("networkResponse", "Failure\n $errorBody")
        }

    } catch (e: HttpException) {
        Log.e("networkResponse", "HttpException\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))

    } catch (e: IOException) {
        Log.e("networkResponse", "IOException\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.check_your_internet_connection)))

    } catch (e: Exception) {
        Log.e("networkResponse", "Exception\n ${e.message.toString()}")
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))
    }
}


// -------------------------------------------------------------- //