package com.maji.mvvm.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maji.mvvm.R
import com.maji.mvvm.databinding.ActivityHistoryBinding
import com.maji.mvvm.util.RecycleViewDivider
import com.maji.mvvm.viewmodel.SubjectViewModel

class HistoryActivity : AppCompatActivity() {
    private val TAG = HistoryActivity::class.java.simpleName
    private var subjectViewModel: SubjectViewModel? = null
    private var viewBinding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)
        initUI()

        observeData()
    }

    private fun initUI() {
        title = getString(R.string.app_history)
        viewBinding?.rvSubject?.addItemDecoration(RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL))
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        viewBinding?.rvSubject?.layoutManager = lm
    }

    private fun observeData() {
        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)

        subjectViewModel?.observeDataMain(this, observer = Observer {
            Log.d(TAG, "history size is = ${it?.size}")
            it?.let {
                val adapter = SubjectAdapter(it)
                viewBinding?.rvSubject?.adapter = adapter
                adapter.notifyDataSetChanged()
            }

        })
    }

}