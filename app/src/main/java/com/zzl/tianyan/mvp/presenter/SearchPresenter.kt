package com.zzl.tianyan.mvp.presenter

import com.zzl.tianyan.base.BasePresenter
import com.zzl.tianyan.mvp.contract.SearchContract
import com.zzl.tianyan.mvp.model.SearchModel
import com.zzl.tianyan.net.exception.ExceptionHandle

class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {
    private var nextPageUrl: String? = null
    private val searchModel by lazy { SearchModel() }


    /**
     * 获取热门关键字的数据
     */
    override fun requestHotWordData() {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.requestHotWordData()
                .subscribe({ string ->
                    mRootView?.apply {
                        setHotWordData(string)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                }
                ))
    }

    /**
     * 查询关键词
     */
    override fun querySearchData(words: String) {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = searchModel.getSearchResult(words)
                .subscribe({ issue ->
                    mRootView?.apply {
                        hideLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        } else
                            setEmptyView()
                    }
                }, { throwable ->
                    mRootView?.apply {
                        hideLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })
        )
    }

    /**
     * 加载更多
     */
    override fun loadMoreData() {
        checkViewAttached()
        nextPageUrl?.let {
            addSubscription(disposable = searchModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            //处理异常
                            showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                        }
                    }))
        }
    }

}