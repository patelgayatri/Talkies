package com.nikunj.talkies.network

import com.nikunj.talkies.models.AddFavouriteModel
import com.nikunj.talkies.models.MovieDetailModel
import com.nikunj.talkies.models.DashModel
import com.nikunj.talkies.models.ResultFavouriteModel
import retrofit2.Call
import retrofit2.http.*

interface DataService {


    @GET("account/9058798/favorite/movies")
    fun getFavouriteList(@Query("api_key") api: String, @Query("session_id") session_id: String): Call<DashModel>


    @POST("account/9058798/favorite")
    fun makeFavourite(@Body addFavourite: AddFavouriteModel, @Query("api_key") api_key: String, @Query("session_id") session_id: String): Call<ResultFavouriteModel>

    @GET("movie/top_rated")
    fun getPopularMovies(@Query("api_key") api: String,@Query("page")page:Int): Call<DashModel>

    @GET("trending/movie/week")
    fun getTrendingMovies(@Query("api_key") api: String): Call<DashModel>

    @GET("movie/{movie_id}")
    fun getMoviesList(@Path("movie_id") movie_id: Int, @Query("api_key") api: String): Call<MovieDetailModel>
}