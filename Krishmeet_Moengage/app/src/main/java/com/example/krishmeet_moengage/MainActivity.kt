package com.example.krishmeet_moengage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var news_rv:RecyclerView
    private lateinit var dataSet:List<NewsArticle>
    private lateinit var signOut: TextView
    private lateinit var auth : FirebaseAuth

    @SuppressLint("MissingInflatedId")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        news_rv = findViewById(R.id.rv_news)
        news_rv.layoutManager = LinearLayoutManager(this@MainActivity)
        news_rv.setHasFixedSize(true)
        signOut = findViewById(R.id.signOut)

        //for singing out from profile
        signOut.setOnClickListener {auth.signOut()
            val intent = Intent(this,login_activity::class.java)
            finish()
            startActivity(intent)

            Toast.makeText(this@MainActivity, "Signing out", Toast.LENGTH_SHORT).show()
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                dataSet = fetchDataInBackground()
                for (i in dataSet) {
                    Log.i("news", i.description)
                }

                news_rv.adapter = rv_adapter(dataSet)

                Toast.makeText(this@MainActivity, "Data fetched", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching data: ${e.message}")
            }
        }
    }

    private suspend fun fetchDataInBackground(): List<NewsArticle> = withContext(Dispatchers.IO) {
        return@withContext fetchNewsData()
    }

    private fun fetchNewsData(): List<NewsArticle> {
        val apiUrl = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

        val url = URL(apiUrl)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStreamReader = InputStreamReader(connection.inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val response = StringBuilder()

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                response.append(line)
            }

            bufferedReader.close()

            // Parse JSON response and create a list of NewsArticle objects
            return parseJsonResponse(response.toString())
        } else {
            throw Exception("Failed to fetch data from the API. Response Code: $responseCode")
        }
    }

    private fun parseJsonResponse(jsonString: String): List<NewsArticle> {
        val articles = mutableListOf<NewsArticle>()

        val jsonObject = org.json.JSONObject(jsonString)
        val articlesArray = jsonObject.getJSONArray("articles")

        for (i in 0 until articlesArray.length()) {
            val articleObject = articlesArray.getJSONObject(i)

            val sourceObject = articleObject.getJSONObject("source")
            val newsSource = NewsSource(
                id = sourceObject.optString("id", null),
                name = sourceObject.getString("name")
            )

            val newsArticle = NewsArticle(
                title = articleObject.getString("title"),
                description = articleObject.getString("description"),
                url = articleObject.getString("url"),
                urlToImage = articleObject.getString("urlToImage"),
                author = articleObject.optString("author", null),
                publishedAt = articleObject.getString("publishedAt"),
                source = newsSource
            )
            articles.add(newsArticle)
        }
        return articles
    }

}