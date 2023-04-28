package com.example.le_androidapp.data;

sealed interface ConnectionState{
    data class Connected(val xVal: Float, val yVal: Float, val zVal: Float): ConnectionState
    object Disconnected: ConnectionState
    object Uninitialized: ConnectionState
    object CurrentlyInitializing: ConnectionState
}