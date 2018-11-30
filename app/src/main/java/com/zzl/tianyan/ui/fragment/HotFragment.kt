package com.zzl.tianyan.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import com.zzl.tianyan.R
import com.zzl.tianyan.base.BaseFragment
import com.zzl.tianyan.base.BaseFragmentAdapter
import com.zzl.tianyan.mvp.contract.HotTabContract
import com.zzl.tianyan.mvp.model.bean.TabInfoBean
import com.zzl.tianyan.mvp.presenter.HotTabPresenter
import com.zzl.tianyan.net.exception.ErrorStatus
import com.zzl.tianyan.showToast
import com.zzl.tianyan.utils.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

class HotFragment : BaseFragment(), HotTabContract.View {

    private val mPresenter by lazy { HotTabPresenter() }
    private var mTitle: String? = null

    /**
     * 存放 tab 标题
     */
    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()

        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RankFragment.getInstance(it.apiUrl) }

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList, mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
