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
                if(recyclerView.childCount< 10) {
                    viewBinding.btnHistory.visibility = View.VISIBLE
                    return
                }
                var isSignificantDelta = abs(dy) > mScrollThreshold;
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
            if(it.isNullOrEmpty()) return@Observer
            Log.d(TAG, "history size is = ${it.size}")
            it.let {
                if(viewBinding.rvSubject.adapter == null) {
                    SubjectAdapter.bindAdapter(viewBinding.rvSubject, it.toMutableList())
                }
                viewBinding.rvSubject.adapter?.notifyDataSetChanged()
            }

        })
    }

}