package com.maji.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maji.mvvm.R
import com.maji.mvvm.util.RecycleViewDivider
import com.maji.mvvm.viewmodel.SubjectViewModel
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var subjectViewModel: SubjectViewModel? = null
    private var recyclerViewSubject: RecyclerView? = null
    private var btnHistory:Button? = null
    private val mScrollThreshold = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

        observeData()
    }

    private fun initUI() {
        title = getString(R.string.app_main)
        btnHistory = findViewById(R.id.btn_history)
        recyclerViewSubject = findViewById(R.id.rv_subject)
        recyclerViewSubject?.addItemDecoration(RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL))
        val lm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerViewSubject?.layoutManager = lm

        recyclerViewSubject?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(recyclerView.childCount< 10) {
                    onScrollDown()
                    return
                }
                var isSignificantDelta = abs(dy) > mScrollThreshold;
                if (isSignificantDelta) {
                    if (dy > 0) {
                        onScrollUp()
                    } else {
                        onScrollDown()
                    }
                }
            }
        })

        btnHistory?.setOnClickListener {
            var intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onScrollUp() {
        btnHistory?.visibility = View.GONE
    }

    private fun onScrollDown() {
        btnHistory?.visibility = View.VISIBLE
    }


    private fun observeData() {
        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)

        subjectViewModel?.observeDataMain(this, observer = Observer {
            Log.d(TAG, "history size is = ${it?.size}")
            it?.let {
                val adapter = SubjectAdapter(it)
                recyclerViewSubject?.adapter = adapter
                adapter.notifyDataSetChanged()
            }

        })
    }

}