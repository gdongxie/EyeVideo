package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class RankModel {
    /**
     * 获取排行榜
     */
    fun requestRankList(apiUrl:String): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getIssueData(apiUrl)
                .compose(SchedulerUtils.ioToMain())
    }
}