package com.nikunj.talkies.Fragment

import HomeAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikunj.talkies.Model.HomeMovie
import com.nikunj.talkies.Model.ResultsModel
import com.nikunj.talkies.R
import com.nikunj.talkies.network.DataService
import com.nikunj.talkies.network.ServiceBuilder
import kotlinx.android.synthetic.main.detail_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragment : Fragment() {
    private var counter: Int? = null
    private var twoPane: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)

        val requestCall: Call<HomeMovie> = dataService.getPopularMovies(ServiceBuilder.apiKey)

        requestCall.enqueue(object  : Callback<HomeMovie> {
            override fun onResponse(
                call: Call<HomeMovie>,
                response: Response<HomeMovie>
            ) {

                var movieDetails = response.body()
                var movieList: List<ResultsModel> = movieDetails?.results as List<ResultsModel>
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
        fun newInstance(counter: Int?): PopularFragment {
            val fragment = PopularFragment()
            val args = Bundle()
            args.putInt(ARG_COUNT, counter!!)
            fragment.arguments = args
            return fragment
        }
    }
}