package com.rizkyadi.myanimelist.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object{
        val LOADING: NetworkState
        val LOADED: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init{
            LOADING= NetworkState(Status.RUNNING,"Running")
            LOADED = NetworkState(Status.SUCCESS,"Success")
            ERROR= NetworkState(Status.FAILED,"Something went wrong")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end")
        }
    }
}