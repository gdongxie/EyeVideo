package com.zzl.tianyan.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity(var title: String, private var selectIcon: Int, private var unSelectIcon: Int) : CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}