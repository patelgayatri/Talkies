package com.nikunj.talkies.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nikunj.talkies.models.AddFavouriteModel
import com.nikunj.talkies.models.MovieDetailModel
import com.nikunj.talkies.models.ResultFavouriteModel
import com.nikunj.talkies.R
import com.nikunj.talkies.database.DBHelper
import com.nikunj.talkies.network.DataService
import com.nikunj.talkies.network.ServiceBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var movieIDValue: Int = 0
    private lateinit var dbHelper: DBHelper
    var isFav=false

    companion object {

        const val Movie_ID = "movie_id"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            movieIDValue = it.getString(Movie_ID)?.toInt()!!

            activity?.toolbar_layout?.title = it.getString(Movie_ID)
        }
        activity?.detail_favorite_fab?.setOnClickListener { view ->
            addToFavourite(view)


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper= container?.context?.let { DBHelper(it) }!!
        isFav=dbHelper.isFavourite(movieIDValue)
        val rootView = inflater.inflate(R.layout.detail, container, false)
        loadDetail(rootView)

        return rootView
    }
    private fun addToFavourite(view: View) {
        val status= !isFav
        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)
        val addFavourite = AddFavouriteModel("movie", movieIDValue,status)
        val requestCall: Call<ResultFavouriteModel> =dataService.makeFavourite(addFavourite,ServiceBuilder.apiKey,ServiceBuilder.Session_ID)

        requestCall.enqueue(object :Callback<ResultFavouriteModel> {


            override fun onResponse(
                call: Call<ResultFavouriteModel>,
                response: Response<ResultFavouriteModel>
            ) {
                val favouriteDetails = response.body()

                Snackbar.make(view, favouriteDetails!!.statusMessage, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()            }

            override fun onFailure(call: Call<ResultFavouriteModel>, t: Throwable) =
                Snackbar.make(view, t.message.toString(), Snackbar.LENGTH_LONG)
                    .show()

        })    }


    private fun loadDetail(rootView: View) {
        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)

        val requestCall: Call<MovieDetailModel> = dataService.getMoviesList(
            movieIDValue,
            ServiceBuilder.apiKey
        )
        requestCall.enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
            ) {

                val movieDetails = response.body()
                rootView.dtl_txt_overview.text = movieDetails?.overview
                rootView.dtl_txt_homePage.text = movieDetails?.homepage
                rootView.dtl_txt_date.text = movieDetails?.releaseDate
                rootView.dtl_txt_review.text = movieDetails?.voteAverage.toString()
                activity?.toolbar_layout?.title = movieDetails?.title
                val imgUrl: String? = ServiceBuilder.IMAGE_URL + movieDetails?.backdropPath

                Glide.with(rootView)
                    .load(imgUrl)
                    .centerCrop()
                    .into(activity?.toolbar_layout?.detail_image!!)
                if(isFav) {
                    activity?.detail_favorite_fab?.setImageResource(R.drawable.ic_favorite_black_24dp)
                }


            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {

            }
        })


    }



}
