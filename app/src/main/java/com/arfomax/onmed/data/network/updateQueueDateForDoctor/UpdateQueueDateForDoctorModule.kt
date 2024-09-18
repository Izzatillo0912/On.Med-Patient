package com.arfomax.onmed.data.network.updateQueueDateForDoctor

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.updateQueueDateForDoctor.UpdateQueueDateForDoctorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class UpdateQueueDateForDoctorModule {


    @Provides
    @Singleton
    fun provideUpdateQueueDateForDoctorService(retrofit: Retrofit) : UpdateQueueDateForDoctorApi {
        return retrofit.create(UpdateQueueDateForDoctorApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideUpdateQueueStatusForDoctorRepository(
        updateQueueDateForDoctorApi: UpdateQueueDateForDoctorApi
    ): UpdateQueueDateForDoctorRepository {
        return UpdateQueueDateForDoctorRepositoryImpl(updateQueueDateForDoctorApi)
    }

}