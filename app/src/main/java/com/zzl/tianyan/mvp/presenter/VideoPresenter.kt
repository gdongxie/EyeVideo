package com.zzl.tianyan.mvp.presenter

import android.app.Activity
import com.hazz.kotlinmvp.utils.NetworkUtil
import com.zzl.tianyan.MyApplication
import com.zzl.tianyan.base.BasePresenter
import com.zzl.tianyan.dataFormat
import com.zzl.tianyan.mvp.contract.VideoDetailsContract
import com.zzl.tianyan.mvp.model.VideoDetailsModel
import com.zzl.tianyan.mvp.model.bean.HomeBean
import com.zzl.tianyan.net.exception.ExceptionHandle
import com.zzl.tianyan.showToast
import com.zzl.tianyan.utils.DisplayManager

class VideoPresenter : BasePresenter<VideoDetailsContract.View>(), VideoDetailsContract.Presenter {

    private val videoDetailsModel: VideoDetailsModel by lazy {
        VideoDetailsModel()
    }

    /**
     * 加载视频相关的数据
     */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo

        val netType = NetworkUtil.isWifi(MyApplication.context)

        //检测是否绑定View
        checkViewAttached()
        if (playInfo!!.size > 1) {
            // 当前网络是 Wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        //Todo 待完善
                        (mRootView as Activity).showToast("本次消耗${(mRootView as Activity)
                                .dataFormat(i.urlList[0].size)}流量")
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }


        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }
        mRootView?.setVideoInfo(itemInfo)


    }

    /***
     * 请求相关数据的视频数据
     */
    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailsModel.requestRelatedData(id)
                .subscribe({ issue ->
                    mRootView?.apply {
                        hideLoading()
                        setRecentRelatedVideo(issue.itemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        hideLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })

        addSubscription(disposable)
    }


}