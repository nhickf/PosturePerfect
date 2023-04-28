package com.example.finaldb.di

import com.example.finaldb.repository.ModeRepository
import com.example.finaldb.repository.ModeRepositoryImpl
import com.example.finaldb.repository.ReadByDeviceRepository
import com.example.finaldb.repository.ReadByDeviceRepositoryImpl
import com.example.finaldb.repository.ResultRepository
import com.example.finaldb.repository.ResultRepositoryImpl
import com.example.finaldb.repository.UserRepository
import com.example.finaldb.repository.UserRepositoryImpl
import com.example.finaldb.source.AppDatabase

object SimpleDependencyInjection {

    data class Repositories(
        val userRepository: UserRepository,
        val modeRepository: ModeRepository,
        val resultRepository: ResultRepository,
        val readByDeviceRepository: ReadByDeviceRepository
    )

    fun initializeRepositories(appDatabase: AppDatabase): Repositories {
       return Repositories(
            userRepository = UserRepositoryImpl(appDatabase.getUserDao()),
            modeRepository = ModeRepositoryImpl(appDatabase.getModesDao()),
            resultRepository = ResultRepositoryImpl(appDatabase.getResultDao()),
            readByDeviceRepository = ReadByDeviceRepositoryImpl(appDatabase.getReadyByDeviceDao())
        )
    }
}