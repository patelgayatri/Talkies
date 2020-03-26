package com.nikunj.talkies.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikunj.talkies.models.DashModel
import com.nikunj.talkies.models.ResultsDashModel
import com.nikunj.talkies.R
import com.nikunj.talkies.adapter.DashAdapter
import com.nikunj.talkies.network.DataService
import com.nikunj.talkies.network.ServiceBuilder
import kotlinx.android.synthetic.main.detail_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragment : Fragment() {
    private var counter: Int? = null
    private var twoPane: Boolean = false
    private var page = 1
    lateinit var movieList: List<ResultsDashModel>
    private var movieArrayList= ArrayList<ResultsDashModel>()
    private var scrollPosition=0

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
        getData(page)
        dash_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val lm:LinearLayoutManager = dash_recyclerview.layoutManager as LinearLayoutManager
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
        val requestCall: Call<DashModel> =
            dataService.getPopularMovies(ServiceBuilder.apiKey, pageNo)

        requestCall.enqueue(object : Callback<DashModel> {
            override fun onResponse(
                call: Call<DashModel>,
                response: Response<DashModel>
            ) {
                scrollPosition=movieArrayList.size
                val movieDetails = response.body()
                movieList = movieDetails?.results as List<ResultsDashModel>
                movieArrayList.addAll(movieList)
                dash_recyclerview.adapter = DashAdapter(movieArrayList, activity, twoPane)
                dash_recyclerview.scrollToPosition(scrollPosition)
            }

            override fun onFailure(call: Call<DashModel>, t: Throwable) {

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