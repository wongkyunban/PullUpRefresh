package com.example.pulluprefresh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private var mItemList: MutableList<String?> = mutableListOf()
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        mockData()
        initAdapter()
        initScrollListener()
    }

    private fun mockData() {
        for (i in 0 until 10) {
            mItemList.add("Item $i")
        }
    }

    private fun initAdapter() {
        recyclerView.adapter = RecyclerVewAdapter(mItemList)
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if(!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mItemList.size - 1 ){
                    loadMore()
                    isLoading = true
                }
            }
        })
    }
    private fun loadMore(){
        mItemList.add(null)
        recyclerView.adapter?.notifyItemInserted(mItemList.size - 1)
        val handler = Handler()
        handler.postDelayed({
            mItemList.removeAt(mItemList.size - 1)
            recyclerView.adapter?.notifyItemRemoved(mItemList.size)
            for(i in mItemList.size .. mItemList.size+10){
                mItemList.add("Item $i")
            }
            recyclerView.adapter?.notifyDataSetChanged()
            isLoading = false
        },2000)
    }
}