package com.zzl.tianyan.mvp.presenter

import com.zzl.tianyan.base.BasePresenter
import com.zzl.tianyan.mvp.contract.RankContract
import com.zzl.tianyan.mvp.model.RankModel
import com.zzl.tianyan.net.exception.ExceptionHandle

class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val rankModel by lazy { RankModel() }


    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList(apiUrl)
                .subscribe({ issue ->
                    mRootView?.apply {
                        hideLoading()
                        setRankList(issue.itemList)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}