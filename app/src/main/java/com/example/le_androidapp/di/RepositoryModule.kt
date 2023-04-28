package com.example.le_androidapp.di

import android.content.Context
import com.example.finaldb.repository.ModeRepository
import com.example.finaldb.repository.ModeRepositoryImpl
import com.example.finaldb.repository.ReadByDeviceRepository
import com.example.finaldb.repository.ReadByDeviceRepositoryImpl
import com.example.finaldb.repository.ResultRepository
import com.example.finaldb.repository.ResultRepositoryImpl
import com.example.finaldb.repository.UserRepository
import com.example.finaldb.repository.UserRepositoryImpl
import com.example.finaldb.source.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    val applicationScope = CoroutineScope(SupervisorJob())


    @Provides
    @Singleton
    fun providesApplicationDatabase(@ApplicationContext context: Context) : AppDatabase{
        return AppDatabase.getDatabase(context = context, applicationScope)
    }

    @Provides
    fun providesUserRepository(appDatabase: AppDatabase) : UserRepository {
        return UserRepositoryImpl(
            getUserDao = appDatabase.getUserDao()
        )
    }

    @Provides
    fun providesResultRepository(appDatabase: AppDatabase) : ResultRepository {
        return ResultRepositoryImpl(
            appDatabase.getResultDao()
        )
    }

    @Provides
    fun providesModesRepository(appDatabase: AppDatabase) : ModeRepository {
        return ModeRepositoryImpl(
            appDatabase.getModesDao()
        )
    }

    @Provides
    fun providesReadByDeviceRepository(appDatabase: AppDatabase) : ReadByDeviceRepository {
        return ReadByDeviceRepositoryImpl(
            appDatabase.getReadyByDeviceDao()
        )
    }

}