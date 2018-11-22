package com.zzl.tianyan.mvp.model

import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.net.RetrofitManager
import com.zzl.tianyan.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class SearchModel {
    /**
     * 请求热门关键词的数据
     */
    fun requestHotWordData(): Observable<ArrayList<String>> {

        return RetrofitManager.service.getHotWord()
                .compose(SchedulerUtils.ioToMain())
    }


    /**
     * 搜索关键词返回的结果
     */
    fun getSearchResult(words: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getSearchData(words)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }
}