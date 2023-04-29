package com.progress.management.network


interface DataCallback<T> {
    fun onConnectSuccess(result: T)
    fun onConnectFail(result: T?)
}
