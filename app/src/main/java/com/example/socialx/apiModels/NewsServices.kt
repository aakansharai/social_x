package com.example.socialx.apiModels

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=us&apiKey=55c221d18f3e4a28a454fa95fb6058ca
//https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=55c221d18f3e4a28a454fa95fb6058ca

const val BASE_URL = "https://newsapi.org/"
const val API_key = "55c221d18f3e4a28a454fa95fb6058ca"

interface NewsInterface {

    @GET("v2/top-headlines?country=in&apiKey=$API_key")

    public fun getHeadlines(@Query("country") country : String , @Query("page") page : Int ) : Call<News>

}
object NewsService{
    var newsInstance : NewsInterface

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance = retrofit.create(NewsInterface::class.java)
    }

}

//https://newsapi.org/v2/top-headlines&apiKey=55c221d18f3e4a28a454fa95fb6058ca
//https://newsapi.org/v2/top-headlines?country=in&apiKey=$API_key&country=in&page=1