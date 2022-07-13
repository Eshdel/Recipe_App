package com.example.foodrecipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.model.ApiStatus
import com.example.foodrecipe.model.entities.Meal
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseDaoViewModel<T> : ViewModel() {

    private var call: Call<T>? = null

    protected var _status: MutableLiveData<ApiStatus> = MutableLiveData(ApiStatus.NONE)
    val status: LiveData<ApiStatus> get() = _status

    protected val _data = MutableLiveData<T?>()
    val data: LiveData<T?> get() = _data

    open suspend fun loadData(call:Call<T>) {

        if (this.call != null) cancel(this.call!!)

        this.call = call

        _status.value =  ApiStatus.LOADING

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                _data.value = response.body()
                Response(call,response)
                _status.value = ApiStatus.DONE
                _status.value =  ApiStatus.NONE
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Failure(call, t)
                _status.value = ApiStatus.ERROR
                _status.value =  ApiStatus.NONE
            }
        }
        )
    }

    open fun cancel(call:Call<T>) {

        if(call.isCanceled) {
            ApiStatus.NONE
            return
        }

        call.cancel()
    }

    abstract fun Response(call: Call<T>, response: Response<T>)

    abstract fun Failure(call: Call<T>, t:Throwable)
}