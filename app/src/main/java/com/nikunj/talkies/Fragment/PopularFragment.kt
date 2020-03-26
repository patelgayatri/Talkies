package com.nikunj.talkies.Fragment

import HomeAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    var total = 0
    var page = 1
    lateinit var movieList: List<ResultsModel>
    var movieArrayList= ArrayList<ResultsModel>()
    var scrollPosition=0

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
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
        getData(page)
        detail_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    var lm:LinearLayoutManager = detail_list.layoutManager as LinearLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == movieArrayList.size-1) {
                        page++
                        getData( page)

                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }

        })

    }

    private fun getData(pageNo: Int) {

        val dataService: DataService = ServiceBuilder.buildService(DataService::class.java)
        val requestCall: Call<HomeMovie> =
            dataService.getPopularMovies(ServiceBuilder.apiKey, pageNo)

        requestCall.enqueue(object : Callback<HomeMovie> {
            override fun onResponse(
                call: Call<HomeMovie>,
                response: Response<HomeMovie>
            ) {
                scrollPosition=movieArrayList.size
                var movieDetails = response.body()
                movieList = movieDetails?.results as List<ResultsModel>
                movieArrayList.addAll(movieList)
                detail_list.adapter = HomeAdapter(movieArrayList, activity, twoPane)
                detail_list.scrollToPosition(scrollPosition)
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