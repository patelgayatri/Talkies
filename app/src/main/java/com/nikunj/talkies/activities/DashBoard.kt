package com.nikunj.talkies.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.nikunj.talkies.adapter.ViewPagerAdapter
import com.nikunj.talkies.R
import kotlinx.android.synthetic.main.activity_dash.*

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)
        setSupportActionBar(dash_toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        dash_view_pager.adapter = createCardAdapter()
        TabLayoutMediator(
            dash_tabs,dash_view_pager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                setTabTitle(
                    tab,
                    position
                )
            }
        ).attach()
    }

    private fun setTabTitle(tab: TabLayout.Tab, position: Int) {
        when (position) {
            0 -> {
                tab.text = "Favourite"
            }
            1 -> {
                tab.text = "Popular"
            }
            2 -> {
                tab.text = "Trending"
            }
        }
    }

    private fun createCardAdapter(): ViewPagerAdapter {
        return ViewPagerAdapter(this)
    }
}