package com.zzl.tianyan.mvp.contract

import com.zzl.tianyan.base.IBaseView
import com.zzl.tianyan.base.IPresenter
import com.zzl.tianyan.mvp.model.bean.HomeBean

/***
 * 搜索的契约类
 */
interface SearchContract {

    interface View : IBaseView {
        /**
         * 设置热门关键词数据
         */
        fun setHotWordData(string: ArrayList<String>)

        /**
         * 设置搜索关键词返回的结果
         */
        fun setSearchResult(issue: HomeBean.Issue)

        /**
         * 关闭软件盘
         */
        fun closeSoftKeyboard()

        /**
         * 设置空 View
         */
        fun setEmptyView()


        fun showError(errorMsg: String, errorCode: Int)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取热门关键字的数据
         */
        fun requestHotWordData()

        /**
         * 查询搜索
         */
        fun querySearchData(words: String)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}