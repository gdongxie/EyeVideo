package com.zzl.tianyan.mvp.presenter

import com.zzl.tianyan.base.BasePresenter
import com.zzl.tianyan.mvp.contract.FollowContract
import com.zzl.tianyan.mvp.model.FollowModel
import com.zzl.tianyan.net.exception.ExceptionHandle

class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val followModel by lazy {
        FollowModel()
    }
    private var nextPageUrl: String? = null
    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = followModel.requestFollowList()
                .subscribe({ issue ->
                    mRootView?.apply {
                        hideLoading()
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setFollowInfo(issue)
                        }

                    }, { t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                        }
                    })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }

}