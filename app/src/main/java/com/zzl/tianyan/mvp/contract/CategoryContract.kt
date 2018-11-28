package com.zzl.tianyan.mvp.contract

import com.zzl.tianyan.base.IBaseView
import com.zzl.tianyan.base.IPresenter
import com.zzl.tianyan.mvp.model.bean.CategoryBean

interface CategoryContract {

    interface View : IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}