package com.zzl.tianyan.view.recyclerview

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
