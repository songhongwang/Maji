package com.maji.mvvm.net

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseCallBack<T>: Callback<T> {
    override fun onFailure(call: Call<T>?, t: Throwable?) {
        onCFailure(call, t, LogicModel(-1))
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        onCResponse(call, response)
    }

    abstract fun onCFailure(call: Call<T>?, t: Throwable?, l:LogicModel?)
    abstract fun onCResponse(call: Call<T>?, response: Response<T>?)

    /**
     * 统一拦截处理 [重复登录，到期续费等]
     */
    class LogicModel(var code:Int)
}