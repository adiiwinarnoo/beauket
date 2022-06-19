@file:Suppress("DEPRECATION")

package com.healstationlab.design.fragment

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.healstationlab.design.R
import com.healstationlab.design.fragment_nesting.AllFragment
import com.healstationlab.design.fragment_nesting.RankFragment
import com.healstationlab.design.fragment_nesting.RecommendFragment
import com.healstationlab.design.fragment_nesting.SaleFragment


class ShoppingFragment : Fragment() {

    val allFragment = AllFragment()
    val recommendFragment = RecommendFragment()
    val saleFragment = SaleFragment()
    val rankFragment = RankFragment()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)
        retainInstance = true

        val bundle = arguments
        val id = bundle?.getInt("id")

        /** tab setting **/
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.tab1)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.tab2)
        tabLayout.getTabAt(2)!!.setIcon(R.drawable.tab3)
        tabLayout.getTabAt(3)!!.setIcon(R.drawable.tab4)

        tabLayout.changeTabsFont(0)

        if(id != null) {
            tabLayout.getTabAt(id)!!.select()
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_frame, recommendFragment).commitAllowingStateLoss()
        } else {
            val fragmentManager = childFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_frame, allFragment).commitAllowingStateLoss()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(context!!, R.color.un_select)
                tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabIconColor = ContextCompat.getColor(context!!, R.color.main_theme)
                tab!!.icon!!.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN)
                val transaction : FragmentTransaction = childFragmentManager.beginTransaction()

                when(tab.position){
                    0 -> transaction.replace(R.id.fragment_frame, allFragment).commitAllowingStateLoss()
                    1 -> transaction.replace(R.id.fragment_frame, recommendFragment).commitAllowingStateLoss()
                    2 -> transaction.replace(R.id.fragment_frame, saleFragment).commitAllowingStateLoss()
                    3 -> transaction.replace(R.id.fragment_frame, rankFragment).commitAllowingStateLoss()
                }

                tab.position.let {
                    tabLayout.changeTabsFont(it)
                }
            }
        })
        return view
    }

    fun TabLayout.changeTabsFont(selectPosition: Int) {
        val vg = this.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            vgTab.forEachIndexed { index, _ ->
                val tabViewChild = vgTab.getChildAt(index)
                if (tabViewChild is TextView) {
                    tabViewChild.setTextBold(j == selectPosition)
                }
            }
        }
    }

    private fun TextView.setTextBold(isBold: Boolean) {
        this.setTypeface(this.typeface, if(isBold) Typeface.BOLD else Typeface.NORMAL)
    }
}