package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/***
 * 首页精选Model
 */
class HomeModel {
    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(num: Int): Observable<HomeBean> {
        return RetrofitManager.service.getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())

    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean> {
        return RetrofitManager.service.getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }

}