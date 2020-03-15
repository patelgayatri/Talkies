package com.nikunj.talkies.network

import com.nikunj.talkies.Model.AddFavourite
import com.nikunj.talkies.Model.DetailMovie
import com.nikunj.talkies.Model.HomeMovie
import com.nikunj.talkies.Model.ResultFavourite
import retrofit2.Call
import retrofit2.http.*

interface DataService {


    @GET("account/9058798/favorite/movies")
    fun getFavouriteList(@Query("api_key") api: String, @Query("session_id") session_id: String): Call<HomeMovie>


    @POST("account/9058798/favorite")
    fun makeFavourite(@Body addFavourite: AddFavourite, @Query("api_key") api_key: String, @Query("session_id") session_id: String): Call<ResultFavourite>

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") api: String): Call<HomeMovie>

    @GET("trending/movie/week")
    fun getTrendingMovies(@Query("api_key") api: String): Call<HomeMovie>

    @GET("movie/{movie_id}")
    fun getMoviesList(@Path("movie_id") movie_id: Int, @Query("api_key") api: String): Call<DetailMovie>
}