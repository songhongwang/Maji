package com.maji.mvvm

import android.util.Log
import androidx.annotation.UiThread
import androidx.test.espresso.base.MainThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.maji.mvvm.model.Subject
import com.maji.mvvm.net.ApiClient
import com.maji.mvvm.net.BaseCallBack
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @MainThread
    fun useAppContext() {

        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ApiClient.service.getList().observeForever(androidx.lifecycle.Observer {
                Log.d("log_jnit_ui", "${it?.authorizations_url}")

            })
        }

        Thread.sleep(2000)

    }
}