package com.njoro.spin.utils

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Online,
        Unavailable,
        Losing,
        Lost
    }
}