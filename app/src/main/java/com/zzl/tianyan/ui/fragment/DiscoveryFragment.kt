package com.zzl.tianyan.ui.fragment


import android.os.Bundle
import com.zzl.tianyan.R
import com.zzl.tianyan.base.BaseFragment


class DiscoveryFragment : BaseFragment() {
    override fun lazyLoad() {

    }


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

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    override fun initView() {

    }
}
