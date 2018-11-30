package com.zzl.tianyan.mvp.contract

import com.zzl.tianyan.base.IBaseView
import com.zzl.tianyan.base.IPresenter
import com.zzl.tianyan.mvp.model.bean.TabInfoBean

interface HotTabContract {

    interface View : IBaseView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}