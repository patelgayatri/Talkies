package com.nikunj.talkies.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.nikunj.talkies.Model.AddFavourite
import com.nikunj.talkies.Model.DetailMovie
import com.nikunj.talkies.Model.ResultFavourite
import com.nikunj.talkies.R
import com.nikunj.talkies.network.DataService
import com.nikunj.talkies.network.ServiceBuilder
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailFragment : Fragment() {
    private var movieIDvalue: Int = 0
    companion object {

        
        const val Movie_ID = "movie_id"
        private const val ARG_COUNT = "param1"
        @JvmStatic
        fun newInstance(counter: Int?): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putInt(ARG_COUNT, counter!!)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            movieIDvalue = it.getString(Movie_ID)?.toInt()!!

            activity?.toolbar_layout?.title = it.getString(Movie_ID)
        }
        activity?.fab?.setOnClickListener { view ->
            addToFavourite(view)


        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.detail, container, false)
        loadDetail(rootView)

        return rootView
    }
    private fun addToFavourite(view: View) {
        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)

        val addFavourite = AddFavourite("movie", movieIDvalue,true)
        val requestCall: Call<ResultFavourite> =dataService.makeFavourite(addFavourite,ServiceBuilder.apiKey,ServiceBuilder.Session_ID)

        requestCall.enqueue(object :Callback<ResultFavourite> {


            override fun onResponse(
                call: Call<ResultFavourite>,
                response: Response<ResultFavourite>
            ) {
                val favouriteDetails = response.body()

                Snackbar.make(view, favouriteDetails!!.statusMessage, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()            }

            override fun onFailure(call: Call<ResultFavourite>, t: Throwable) {
                Snackbar.make(view, t.localizedMessage, Snackbar.LENGTH_LONG)
                    .show()
            }

        })    }


    private fun loadDetail(rootView: View) {
        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)

        val requestCall: Call<DetailMovie> = dataService.getMoviesList(
            movieIDvalue,
            ServiceBuilder.apiKey
        )
        requestCall.enqueue(object : Callback<DetailMovie> {
            override fun onResponse(
                call: Call<DetailMovie>,
                response: Response<DetailMovie>
            ) {

                val movieDetails = response.body()
                rootView.dtl_txt_overview.text = movieDetails?.overview
                rootView.dtl_txt_homePage.text = movieDetails?.homepage
                rootView.dtl_txt_date.text = movieDetails?.releaseDate
                rootView.dtl_txt_review.text = movieDetails?.voteAverage.toString()
                activity?.toolbar_layout?.title = movieDetails?.title
                val imgUrl: String? = ServiceBuilder.IMAGE_URL + movieDetails?.posterPath

                Glide.with(rootView)
                    .load(imgUrl)
                    .centerCrop()
                    .into(activity?.toolbar_layout?.detail_image!!)


            }

            override fun onFailure(call: Call<DetailMovie>, t: Throwable) {

            }
        })


    }



}
