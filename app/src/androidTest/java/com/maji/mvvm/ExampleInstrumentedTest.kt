package com.maji.mvvm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.base.MainThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.maji.mvvm.db.SubjectDataBase
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
    val TAG = "log_jnit_ui"
    @Test
    @MainThread
    fun testApi() {
        val context = InstrumentationRegistry.getInstrumentation().context
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            ApiClient.service.getList().observeForever(androidx.lifecycle.Observer {
                Log.d(TAG, "response url is ${it?.authorizations_url}")
            })
        }

        Thread.sleep(2000)
    }

    @Test
    fun testDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val db = SubjectDataBase.getInstance(context)
        val result = db?.subjectDao()?.getSubjectSync()

        Log.d(TAG, "history size is ${result?.size}")
    }


}