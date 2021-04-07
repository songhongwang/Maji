package com.maji.mvvm.net

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    /**
     * 实现allAdapter.Factory()的get方法用于返回我们实现的CallAdapter<*>对象
     */
    override fun get(
        returnType: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): CallAdapter<*>  {
        if(returnType !is ParameterizedType){
            throw IllegalArgumentException("返回值需为参数化类型")
        }
        //获取returnType的class类型
        val returnClass = CallAdapter.Factory.getRawType(returnType)
        if(returnClass != RespLiveData::class.java){
            throw IllegalArgumentException("MutableLiveData")
        }
        val type = CallAdapter.Factory.getParameterUpperBound(0, returnType)
        val a = LiveDataCallAdapter(type)
        Log.e("live_data_factory","get request data:${a.responseType()}")
        return a
    }
    /**
     * 请求适配器
     *
     * T:需要返回的数据RespLiveData<T>
     * R:
     */
    /**val map: MutableMap<String,RespLiveData<*>>,**/
    class LiveDataCallAdapter(var type:Type):CallAdapter<RespLiveData<*>>{

        override fun <R>adapt(call: Call<R>?): RespLiveData<*>? {
            var mutableLiveData:RespLiveData<*>? =null
            call?.let {
                Log.e("live_data_factory","开始请求数据${call.request().url()}")
                val requestUrl =call.request().url().encodedPath()
                Log.e("live_data_factory","请求url:$requestUrl")
                mutableLiveData = object : RespLiveData<R>(){
                    override fun onActive() {
                        super.onActive()
                        proxyRequest(call)
                    }
                }
            }
            return mutableLiveData
        }


        override fun responseType(): Type = type

    }

}

/***
 * 泛型T需是BaseResp的子类，对应的api也需要是BaseResp的子类
 */
open class RespLiveData<T>: MutableLiveData<T>() {

    //这个作用是业务在多线程中,业务处理的线程安全问题,确保单一线程作业
    private val flag = AtomicBoolean(false)

    /**
     * 请求回调重写
     */
    fun<R> proxyRequest(callHttp: Call<R>){
        if(flag.compareAndSet(false,true)){
            callHttp.enqueue(object: Callback<R> {
                override fun onFailure(call: Call<R>?, t: Throwable?) {
                    Log.e("live_data_factory","CallAdapter onFailure:${t?.message}")
                    postValue(null)
                }
                override fun onResponse(call: Call<R>?, response: Response<R>?) {
                    if (response?.isSuccessful!!) {
                        try {
                            val a = response.body() as T
                            postValue(a)
                        }catch (e: Exception){
                            e.printStackTrace()
                            postValue(null)
                        }
                    }else{
                        postValue(null)
                    }
                }
            })
        }
    }

}