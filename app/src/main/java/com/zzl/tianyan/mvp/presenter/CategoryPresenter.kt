package com.zzl.tianyan.mvp.presenter

import com.zzl.tianyan.base.BasePresenter
import com.zzl.tianyan.mvp.contract.CategoryContract
import com.zzl.tianyan.mvp.model.CategoryModel
import com.zzl.tianyan.net.exception.ExceptionHandle

class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = categoryModel.getCategoryData()
                .subscribe({ categoryList ->
                    mRootView?.apply {
                        hideLoading()
                        showCategory(categoryList)
                    }
                }, { t ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)
    }
}