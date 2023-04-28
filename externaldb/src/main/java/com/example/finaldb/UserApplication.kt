package com.example.finaldb

import android.app.Application
import com.example.finaldb.di.SimpleDependencyInjection
import com.example.finaldb.repository.UserRepositoryImpl
import com.example.finaldb.source.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { SimpleDependencyInjection.initializeRepositories(database) }
}