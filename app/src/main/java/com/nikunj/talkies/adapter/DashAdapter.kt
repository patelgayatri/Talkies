package com.nikunj.talkies.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nikunj.talkies.activities.DetailActivity
import com.nikunj.talkies.fragment.DetailFragment
import com.nikunj.talkies.R
import com.nikunj.talkies.network.ServiceBuilder.IMAGE_URL
import com.nikunj.talkies.models.ResultsDashModel

import kotlinx.android.synthetic.main.row_dash.view.*

class DashAdapter(
    private val itemsList: List<ResultsDashModel>,
    private val context: FragmentActivity?,
    private val twoPane:Boolean
):
    RecyclerView.Adapter<DashAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {

        onClickListener = View.OnClickListener { v ->
         val movieId: Any = v.tag

            if (twoPane) {
                val fragment = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(DetailFragment.Movie_ID, movieId.toString())
                    }

                }
                context?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.detail_container, fragment)
                    ?.commit()
            } else {
                val intent = Intent(v.context, DetailActivity::class.java).apply {
                    putExtra(DetailFragment.Movie_ID, movieId.toString())
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_dash, parent, false))
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imgUrl: String? = IMAGE_URL + itemsList[position].posterPath
        holder.name.text = itemsList.get(position).title
        holder.review.text = itemsList.get(position).voteAverage.toString()
        Glide.with(holder.itemView)
            .load(imgUrl)
            .into(holder.image)
        val item = itemsList[position]

        with(holder.itemView) {
            tag = item.id
            setOnClickListener(onClickListener)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.row_dash_title
        var review: TextView = view.row_dash_review
        var image: ImageView = view.row_dash_image
    }
}