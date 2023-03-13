package com.example.socialx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class DetailedNews : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_news)

        supportActionBar?.hide()

        val url = intent.getStringExtra("URL")
        var detail = findViewById<WebView>(R.id.detailedNews)
        var progressbar = findViewById<ProgressBar>(R.id.progressbar)


        if(url != null){
            detail.settings.javaScriptEnabled = true
            detail.settings.userAgentString = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
            detail.webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressbar.visibility = View.GONE
                    detail.visibility = View.VISIBLE
                }
            }

            detail.loadUrl(url)
        }
    }
}