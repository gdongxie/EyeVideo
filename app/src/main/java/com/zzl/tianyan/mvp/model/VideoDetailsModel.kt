package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class VideoDetailsModel {
    fun requestRelatedData(id: Long): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }
}