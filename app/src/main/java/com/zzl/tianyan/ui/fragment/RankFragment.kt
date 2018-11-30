package com.zzl.tianyan.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zzl.tianyan.R
import com.zzl.tianyan.base.BaseFragment
import com.zzl.tianyan.mvp.contract.RankContract
import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.mvp.presenter.RankPresenter
import com.zzl.tianyan.net.exception.ErrorStatus
import com.zzl.tianyan.showToast
import com.zzl.tianyan.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.fragment_rank.*

/***
 * 热门排行榜
 */
class RankFragment : BaseFragment(), RankContract.View {

    private val mPresenter by lazy { RankPresenter() }
    private var apiUrl: String? = null
    private val mAdapter by lazy { activity?.let { CategoryDetailAdapter(it, itemList, R.layout.item_category_detail) } }
    private var itemList = ArrayList<HomeBean.Issue.Item>()

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_rank

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()
        mAdapter?.addData(itemList)
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
    }

    override fun hideLoading() {
        multipleStatusView.showLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}