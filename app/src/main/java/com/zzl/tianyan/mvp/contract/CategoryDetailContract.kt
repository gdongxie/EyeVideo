package com.zzl.tianyan.mvp.contract

import com.zzl.tianyan.base.IBaseView
import com.zzl.tianyan.base.IPresenter
import com.zzl.tianyan.mvp.model.bean.HomeBean

/***
 * 分类详情的契约类
 */
interface CategoryDetailContract {


    interface View : IBaseView {
        /**
         *  设置列表数据
         */
        fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        fun getCategoryDetailList(id: Long)

        fun loadMoreData()
    }

}