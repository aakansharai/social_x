package com.example.socialx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.socialx.apiModels.News
import com.example.socialx.apiModels.NewsInterface
import com.example.socialx.apiModels.NewsService
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {
    lateinit var adapter : NewsAdapter
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()
        getNews()

    }

    private fun getNews() {
        val newsList : RecyclerView = findViewById(R.id.mainContainerHome)
        val progress : ProgressBar = findViewById(R.id.progressHome)

        val new = NewsService.newsInstance.getHeadlines("in", 1)
        new.enqueue(object : Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null){
                    progress.visibility = View.GONE
                    newsList.visibility = View.VISIBLE
                    Log.d("AAKA", news.toString())
                    adapter = NewsAdapter(this@Home, news!!.articles)
                    newsList.adapter = adapter
                    newsList.layoutManager = LinearLayoutManager(this@Home)
                }
                else{
                    Log.d("AAKA", "unexpected fetching news Error")
                    progress.visibility = View.GONE

                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("AAKA", "Error in fetching news",t)            }
        })
    }
}