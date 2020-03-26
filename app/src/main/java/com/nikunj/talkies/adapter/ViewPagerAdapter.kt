package com.nikunj.talkies.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nikunj.talkies.fragment.FavouriteFragment
import com.nikunj.talkies.fragment.PopularFragment
import com.nikunj.talkies.fragment.TrendingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavouriteFragment.newInstance(position)
            }
            1 -> {
                PopularFragment.newInstance(position)
            }
            else -> {
                TrendingFragment.newInstance(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return ITEM_SIZE
    }

    companion object {
        private const val ITEM_SIZE = 3
    }
}