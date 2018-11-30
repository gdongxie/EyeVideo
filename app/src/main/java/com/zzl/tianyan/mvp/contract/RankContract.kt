package com.zzl.tianyan.mvp.contract

import com.zzl.tianyan.base.IBaseView
import com.zzl.tianyan.base.IPresenter
import com.zzl.tianyan.mvp.model.bean.HomeBean

interface RankContract {

    interface View : IBaseView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl: String)
    }
}