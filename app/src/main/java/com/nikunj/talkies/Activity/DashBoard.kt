package com.nikunj.talkies.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.nikunj.talkies.Adapter.ViewPagerAdapter
import com.nikunj.talkies.R
import kotlinx.android.synthetic.main.activity_dash.*

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        view_pager.setAdapter(createCardAdapter())
        TabLayoutMediator(
            tabs,view_pager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                setTabTitle(
                    tab,
                    position
                )
            }
        ).attach()
    }

    private fun setTabTitle(tab: TabLayout.Tab, position: Int) {
        if (position == 0) {
            tab.text = "Popular"
        } else if (position == 1) {
            tab.text = "Favourite"
        } else if (position == 2) {
            tab.text = "Trending"
        }
    }

    private fun createCardAdapter(): ViewPagerAdapter {
        return ViewPagerAdapter(this)
    }
}