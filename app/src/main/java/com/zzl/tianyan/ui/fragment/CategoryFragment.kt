package com.zzl.tianyan.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zzl.tianyan.R
import com.zzl.tianyan.base.BaseFragment
import com.zzl.tianyan.mvp.contract.CategoryContract
import com.zzl.tianyan.mvp.model.bean.CategoryBean
import com.zzl.tianyan.mvp.presenter.CategoryPresenter
import com.zzl.tianyan.net.exception.ErrorStatus
import com.zzl.tianyan.showToast
import com.zzl.tianyan.ui.adapter.CategoryAdapter
import com.zzl.tianyan.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : BaseFragment(), CategoryContract.View {

    private var mTitle: String? = null
    private var mCategoryList = ArrayList<CategoryBean>()

    private val mPresenter by lazy { CategoryPresenter() }
    private val mAdapter by lazy { activity?.let { CategoryAdapter(it, mCategoryList, R.layout.item_category) } }

    /**
     * 伴生对象
     */
    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_category

    @Suppress("DEPRECATION")
    override fun initView() {
        mPresenter.attachView(this)

        mLayoutStatusView = multipleStatusView

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                val position = parent.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!

                outRect.set(if (position % 2 == 0) 0 else offset, offset,
                        if (position % 2 == 0) offset else 0, offset)
            }

        })
    }

    override fun lazyLoad() {
        //获取分类信息
        mPresenter.getCategoryData()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        mAdapter?.setData(mCategoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView?.showNoNetwork()
        } else {
            multipleStatusView?.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView?.showLoading()
    }

    override fun hideLoading() {
        multipleStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}