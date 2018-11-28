package com.zzl.tianyan.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import com.zzl.tianyan.R
import com.zzl.tianyan.base.BaseFragment
import com.zzl.tianyan.base.BaseFragmentAdapter
import com.zzl.tianyan.utils.StatusBarUtil
import com.zzl.tianyan.view.recyclerview.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_discovery.*

/**
 * 发现页
 */
class DiscoveryFragment : BaseFragment() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle;
            fragment.mTitle = title;
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_discovery


    override fun initView() {
        //状态栏透明和间距处理
        activity?.let {
            StatusBarUtil.darkMode(it)
        }
        activity?.let {
            StatusBarUtil.setPaddingSmart(it, toolbar)
        }
        tv_header_title.text = mTitle
        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)

    }


    override fun lazyLoad() {

    }
}
