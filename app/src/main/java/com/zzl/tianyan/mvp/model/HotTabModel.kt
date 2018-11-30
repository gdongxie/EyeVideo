package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.TabInfoBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class HotTabModel {

    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }

}