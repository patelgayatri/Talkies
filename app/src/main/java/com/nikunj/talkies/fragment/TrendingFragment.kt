package com.nikunj.talkies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nikunj.talkies.models.DashModel
import com.nikunj.talkies.models.ResultsDashModel
import com.nikunj.talkies.R
import com.nikunj.talkies.adapter.DashAdapter
import com.nikunj.talkies.network.DataService
import com.nikunj.talkies.network.ServiceBuilder
import com.nikunj.talkies.network.ServiceBuilder.apiKey
import kotlinx.android.synthetic.main.detail_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrendingFragment : Fragment() {
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
    ): View? {
        return inflater.inflate(R.layout.fragment_dash, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)

        val requestCall: Call<DashModel> = dataService.getTrendingMovies(apiKey)

        requestCall.enqueue(object : Callback<DashModel> {
            override fun onResponse(
                call: Call<DashModel>,
                response: Response<DashModel>
            ) {

                val movieDetails = response.body()
                val movieList: List<ResultsDashModel> = movieDetails?.results as List<ResultsDashModel>
                dash_recyclerview.layoutManager = LinearLayoutManager(view.context)
                dash_recyclerview.adapter = DashAdapter(movieList, activity, twoPane)

            }

            override fun onFailure(call: Call<DashModel>, t: Throwable) {

            }
        })
    }

    companion object {
        private const val ARG_COUNT = "param1"
        @JvmStatic
        fun newInstance(counter: Int?): TrendingFragment {
            val fragment = TrendingFragment()
            val args = Bundle()
            args.putInt(ARG_COUNT, counter!!)
            fragment.arguments = args
            return fragment
        }
    }
}