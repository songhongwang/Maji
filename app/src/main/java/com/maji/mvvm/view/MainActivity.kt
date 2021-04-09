package com.maji.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.maji.mvvm.R
import com.maji.mvvm.databinding.ActivityMainBinding
import com.maji.mvvm.viewmodel.SubjectViewModel
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var subjectViewModel: SubjectViewModel
    private val mScrollThreshold = 20
    private val maxLinesScrollLinkage = 5

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initUI()

        observeData()
    }

    private fun initUI() {
        title = getString(R.string.app_main)

        viewBinding.rvSubject.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(recyclerView.childCount< maxLinesScrollLinkage) {
                    viewBinding.btnHistory.visibility = View.VISIBLE
                    return
                }
                var isSignificantDelta = abs(dy) > mScrollThreshold
                Log.d(TAG, "scroll $dy")
                if (isSignificantDelta) {
                    if (dy > 0) {
                        viewBinding.btnHistory.visibility = View.GONE
                    } else {
                        viewBinding.btnHistory.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewBinding.btnHistory.setOnClickListener {
            var intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {
        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        subjectViewModel.observeDataMain(this, observer = Observer {
            if(it.isNullOrEmpty()) {
                Log.d(TAG, "main history size is null")
                return@Observer
            }
            Log.d(TAG, "main history size is = ${it.size}")
            it.let {
                var adapter = viewBinding.rvSubject.adapter as? SubjectAdapter
                if(adapter == null) {
                    SubjectAdapter.bindAdapter(viewBinding.rvSubject, it.toMutableList())
                } else {
                    // 防止数据拉取失败 出现重复条目
                    val latest = it[0]
                    val readyLatest = adapter.dataList?.get(0)
                    if(latest?.id == readyLatest?.id) {
                        return@let
                    }
                    // 插入到列表头部
                    viewBinding.rvSubject.scrollToPosition(0)
                    adapter.dataList?.add(0, it[0])
                    adapter.notifyItemInserted(0)
                }
            }

        })
    }

}