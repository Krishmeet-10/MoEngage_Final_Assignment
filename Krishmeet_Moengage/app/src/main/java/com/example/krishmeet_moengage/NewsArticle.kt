package com.example.krishmeet_moengage

data class NewsArticle(val title: String,
                       val description: String,
                       val url: String,
                       val urlToImage: String,
                       val author: String?,
                       val publishedAt: String,
                       val source: NewsSource)

data class NewsSource(
    val id: String?,
    val name: String
)
