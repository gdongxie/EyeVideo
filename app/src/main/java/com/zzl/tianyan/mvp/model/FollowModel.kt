package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class FollowModel {
    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getFollowInfo()
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}