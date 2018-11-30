package com.zzl.tianyan.mvp.presenter

import com.zzl.tianyan.base.BasePresenter
import com.zzl.tianyan.mvp.contract.HotTabContract
import com.zzl.tianyan.mvp.model.HotTabModel
import com.zzl.tianyan.net.exception.ExceptionHandle

class HotTabPresenter : BasePresenter<HotTabContract.View>(), HotTabContract.Presenter {

    private val hotModel by lazy { HotTabModel() }
    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = hotModel.getTabInfo()
                .subscribe({ tabInfo ->
                    mRootView?.setTabInfo(tabInfo)
                }, { throwable ->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
    }
}