package com.maji.mvvm

import android.util.Log
import androidx.lifecycle.Observer
import com.maji.mvvm.model.Subject
import com.maji.mvvm.net.ApiClient
import com.maji.mvvm.net.BaseCallBack
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun server_isCorrect() {

        ApiClient.service.getList().observeForever {
            Log.d("test_server", "${it?.authorizations_url}")
        }


        // 等待异步接口执行完毕
        Thread.sleep(5000)

    }

}