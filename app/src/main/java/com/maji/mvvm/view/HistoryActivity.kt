package com.maji.mvvm.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maji.mvvm.R
import com.maji.mvvm.databinding.ActivityHistoryBinding
import com.maji.mvvm.viewmodel.SubjectViewModel

class HistoryActivity : AppCompatActivity() {
    private val TAG = HistoryActivity::class.java.simpleName
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var viewBinding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initUI()

        observeData()
    }

    private fun initUI() {
        title = getString(R.string.app_history)
    }

    private fun observeData() {
        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        subjectViewModel.observeDataHistory(this, o = Observer {
            it?.let {
                if(it.isEmpty()) return@let
                Log.d(TAG, "history size is = ${it.size}")

                if(viewBinding.rvSubject.adapter == null) {
                    SubjectAdapter.bindAdapter(viewBinding.rvSubject, it.toMutableList())
                }
            }

        })
    }

}