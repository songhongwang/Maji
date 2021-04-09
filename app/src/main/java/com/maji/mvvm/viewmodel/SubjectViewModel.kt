package com.maji.mvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.maji.mvvm.db.SubjectDataBase
import com.maji.mvvm.model.Subject
import com.maji.mvvm.net.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SubjectViewModel(context: Application) : AndroidViewModel(context) {
    private val TAG = SubjectViewModel::class.java.simpleName
    private val timer = Timer("schedule", true);
    private var db:SubjectDataBase? = null
    private val period = 5 * 1000L
    private var sdf :SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:MM:SS", Locale.CHINESE)
    private var liveDataMain = MutableLiveData<List<Subject?>?>()

    init {
        db = SubjectDataBase.getInstance(context)
    }

    private fun getData(owner: LifecycleOwner, observer: Observer<Subject?>) {
        ApiClient.service.getList().observeForever(observer)
    }

    fun observeDataHistory(owner: LifecycleOwner, o: Observer<List<Subject?>?>) {
        querySubjectHistory(owner, o)
    }

    fun observeDataMain(owner: LifecycleOwner, observer: Observer<List<Subject?>?>) {
        liveDataMain.observe(owner, observer)
        refreshData()

        timer.schedule(object : TimerTask(){
            override fun run() {
                GlobalScope.launch(Dispatchers.Main) {
                    getData(owner, observer = Observer {
                        Log.d(TAG, "getData $it")

                        it?.let { it1 -> insertDB(it1) }
                    })

                    refreshData()
                }
            }
        }, 0, period)
    }

    private fun refreshData() {
        val syncData = db?.subjectDao()?.getSubjectSync()
        if(syncData.isNullOrEmpty()) {
            Log.d(TAG, "syncData is null")
            return
        }
        Log.d(TAG, "syncData ${syncData[0]?.create_time}")
        liveDataMain.postValue(syncData)
    }

    private fun insertDB(subject: Subject) {
        Log.d(TAG, "insertDB")

        subject.create_time =  sdf.format(Date())
        db?.subjectDao()?.insertSubject(subject)
    }

    private fun querySubjectHistory(owner: LifecycleOwner, observer: Observer<List<Subject?>?>) {
        db?.subjectDao()?.getSubject()?.observe(owner, observer)
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}