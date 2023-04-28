package com.example.le_androidapp.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.example.finaldb.repository.ModeRepository
import com.example.finaldb.repository.ReadByDeviceRepository
import com.example.finaldb.repository.ResultRepository
import com.example.finaldb.repository.UserRepository
import com.example.le_androidapp.domain.DeviceViewModel
import com.example.le_androidapp.data.DeviceReceiveManager
import com.example.le_androidapp.data.ble.DeviceBLEReceiveManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBluetoothAdapter(@ApplicationContext context: Context):BluetoothAdapter{
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return manager.adapter
    }

    @Provides
    @Singleton
    fun provideTempHumidityReceiveManager(
        @ApplicationContext context: Context,
        bluetoothAdapter: BluetoothAdapter
    ):DeviceReceiveManager{
        return DeviceBLEReceiveManager(bluetoothAdapter,context)
    }

    @Provides
    @Singleton
    fun provideDeviceModel(
        deviceReceiveManager: DeviceReceiveManager,
        readByDeviceRepository: ReadByDeviceRepository,
        modesRepository: ModeRepository,

    ) : DeviceViewModel {
        return DeviceViewModel(deviceReceiveManager,readByDeviceRepository,modesRepository)
    }

}