package com.nikunj.talkies.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nikunj.talkies.fragment.DetailFragment
import com.nikunj.talkies.R
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {

            val fragment = DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        DetailFragment.Movie_ID,
                        intent.getStringExtra(DetailFragment.Movie_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.detail_container, fragment)
                .commit()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {

                navigateUpTo(Intent(this, DashBoard::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}



