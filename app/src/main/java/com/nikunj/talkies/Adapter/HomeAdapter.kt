
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
import com.nikunj.talkies.Fragment.DetailFragment
import com.nikunj.talkies.R
import com.nikunj.talkies.network.ServiceBuilder.IMAGE_URL
import com.nikunj.talkies.Model.ResultsModel

import kotlinx.android.synthetic.main.row_home.view.*

class HomeAdapter(
    val items: List<ResultsModel>,
    val context: FragmentActivity?,
    private val twoPane:Boolean
):
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        println("init called home")

        onClickListener = View.OnClickListener { v ->
            // val item = v.tag as DummyContent.DummyItem
         var movieId= v.tag

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
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var imgUrl: String? = IMAGE_URL + items.get(position).posterPath
        holder.name.text = items.get(position).title
        holder.review.text = items.get(position).voteAverage.toString()
        Glide.with(holder.itemView)
            .load(imgUrl)
            .into(holder.image)
        val item = items[position]

        with(holder.itemView) {
            tag = item.id
            setOnClickListener(onClickListener)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.row_home_title
        var review: TextView = view.row_home_review
        var image: ImageView = view.row_home_image
    }
}