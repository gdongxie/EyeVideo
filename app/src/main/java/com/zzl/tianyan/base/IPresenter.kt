package com.zzl.tianyan.base

/***
 * presenter基类
 */
interface IPresenter<in V : IBaseView> {
    fun attachView(mRootView: V)

    fun detachView()
}