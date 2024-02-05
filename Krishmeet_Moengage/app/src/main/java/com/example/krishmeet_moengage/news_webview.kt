package com.example.krishmeet_moengage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast

class news_webview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_webview)

        val url = intent.getStringExtra("url")
        if (url != null) {
            Log.i("newslink",url)
        }

        val webView: WebView = findViewById(R.id.webView)

// Enable JavaScript if your webpage requires it
        webView.settings.javaScriptEnabled = true

// Load the URL into the WebView
        if (url != null) {
            webView.loadUrl(url)
        }
    }
}