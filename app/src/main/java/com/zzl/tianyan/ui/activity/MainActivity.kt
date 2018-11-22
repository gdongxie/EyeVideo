package com.zzl.tianyan.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zzl.tianyan.R
import com.zzl.tianyan.mvp.model.bean.TabEntity
import com.zzl.tianyan.base.BaseActivity
import com.zzl.tianyan.showToast
import com.zzl.tianyan.ui.fragment.DiscoveryFragment
import com.zzl.tianyan.ui.fragment.HomeFragment
import com.zzl.tianyan.ui.fragment.HotFragment
import com.zzl.tianyan.ui.fragment.MyFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    //标题
    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")
    //为选中图标
    private val mIconUnSeLectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    //选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)

    private val mTabEntities = ArrayList<CustomTabEntity>()

    private var mHomeFragemnt: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMyFragment: MyFragment? = null
    //默认为0
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }

    /***
     * 初始化底部菜单
     */
    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSeLectIds[it])
        }
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }

        })

    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 //首页
            -> mHomeFragemnt?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragemnt = it
                transaction.add(R.id.fl_container, it, "home")
            }
            1 //发现
            -> mDiscoveryFragment?.let {
                transaction.show(it)
            } ?: DiscoveryFragment.getInstance(mTitles[position]).let {
                mDiscoveryFragment = it
                transaction.add(R.id.fl_container, it, "discovery")
            }
            2 //热门
            -> mHotFragment?.let {
                transaction.show(it)
            } ?: HotFragment.getInstance(mTitles[position]).let {
                mHotFragment = it
                transaction.add(R.id.fl_container, it, "hot")
            }
            3 //我的
            -> mMyFragment?.let {
                transaction.show(it)
            } ?: MyFragment.getInstance(mTitles[position]).let {
                mMyFragment = it
                transaction.add(R.id.fl_container, it, ",mine")
            }
            else -> {

            }
        }

        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()

    }

    /**
     * 隐藏所有Fragment
     * @param transaction FragmentTransaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragemnt?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mHotFragment?.let { transaction.hide(it) }
        mMyFragment?.let { transaction.hide(it) }
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
//        showToast("onSaveInstanceState->"+mIndex)
//        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        if (tab_layout != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
