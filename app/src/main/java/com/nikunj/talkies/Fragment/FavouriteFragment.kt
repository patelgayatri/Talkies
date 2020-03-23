package com.nikunj.talkies.Fragment

import HomeAdapter
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikunj.talkies.database.DBHelper
import com.nikunj.talkies.Model.AddFavourite
import com.nikunj.talkies.Model.HomeMovie
import com.nikunj.talkies.Model.ResultsModel
import com.nikunj.talkies.R
import com.nikunj.talkies.network.DataService
import com.nikunj.talkies.network.ServiceBuilder
import com.nikunj.talkies.network.ServiceBuilder.Session_ID
import com.nikunj.talkies.network.ServiceBuilder.apiKey
import kotlinx.android.synthetic.main.detail_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouriteFragment : Fragment() {
    private var counter: Int? = null
    private var twoPane: Boolean = false
    lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //dbHelper = DBHelper(this)

        if (arguments != null) {
            counter = arguments!!.getInt(ARG_COUNT)
        }
        if (detail_container != null) {
            twoPane = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper= DBHelper(view.context)

        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)

        val requestCall: Call<HomeMovie> = dataService.getFavouriteList(apiKey, Session_ID)

        requestCall.enqueue(object  : Callback<HomeMovie> {
            override fun onResponse(
                call: Call<HomeMovie>,
                response: Response<HomeMovie>
            ) {

                var movieDetails = response.body()
                var movieList: List<ResultsModel> = movieDetails?.results as List<ResultsModel>
                for(item in movieList) {
                    dbHelper.addFavourite(AddFavourite(item.title!!, item.id!!,true))

                }
                detail_list.layoutManager = LinearLayoutManager(view.context)
                detail_list.adapter = HomeAdapter(movieList, activity,twoPane)

            }

            override fun onFailure(call: Call<HomeMovie>, t: Throwable) {

            }
        })
    }

    companion object {
        private const val ARG_COUNT = "param1"
        @JvmStatic
        fun newInstance(counter: Int?): FavouriteFragment {
            val fragment = FavouriteFragment()
            val args = Bundle()
            args.putInt(ARG_COUNT, counter!!)
            fragment.arguments = args
            return fragment
        }
    }
}