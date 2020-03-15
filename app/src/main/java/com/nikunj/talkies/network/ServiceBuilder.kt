package com.nikunj.talkies.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    const val Session_ID="2f7e9c42f2265a23f2d7e591f7773df6909281e7"
    private const val URL="https://api.themoviedb.org/3/"
    const val IMAGE_URL="https://image.tmdb.org/t/p/original"
    const val apiKey="6c10e3ba91996904d5bd039040cca8ea"

    private val okHttp: OkHttpClient.Builder= OkHttpClient.Builder()

    private val builder: Retrofit.Builder =Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())


    private val retrofit:Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }
}