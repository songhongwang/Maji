package com.maji.mvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
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

    init {
        db = SubjectDataBase.getInstance(context)
    }

    private fun getData(owner: LifecycleOwner, observer: Observer<Subject?>) {
        ApiClient.service.getList().observe(owner, observer)
    }

    fun observeDataHistory(owner: LifecycleOwner, observer: Observer<List<Subject?>?>) {
        querySubjectHistory(owner, observer)

        timer.schedule(object : TimerTask(){
            override fun run() {
                Log.d(TAG, "周期5s")

                GlobalScope.launch(Dispatchers.Main) {
                    getData(owner, observer = Observer {
                        it?.let { it1 -> insertDB(it1) }

                        // 是否直接刷新数据
                         querySubjectHistory(owner, observer)
                    })
                }

            }
        }, period, period)
    }

    fun observeDataMain(owner: LifecycleOwner, observer: Observer<List<Subject?>?>) {
        querySubjectHistory(owner, observer)

        timer.schedule(object : TimerTask(){
            override fun run() {
                Log.d(TAG, "5s latter")

                GlobalScope.launch(Dispatchers.Main) {
                    getData(owner, observer = Observer {
                        it?.let { it1 -> insertDB(it1) }
                    })
                }

            }
        }, period)
    }

    private fun insertDB(subject: Subject) {
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