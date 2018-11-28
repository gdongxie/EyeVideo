package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.CategoryBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class CategoryModel {

    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }
}