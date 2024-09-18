package com.arfomax.onmed.data.network.addQueueForDoctor

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.addQueueForDoctor.AddQueueForDoctorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class AddQueueForDoctorModule {


    @Provides
    @Singleton
    fun provideAddQueueForDoctorService(retrofit: Retrofit) : AddQueueForDoctorApi {
        return retrofit.create(AddQueueForDoctorApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideAddQueueForDoctorRepository(addQueueForDoctorApi: AddQueueForDoctorApi): AddQueueForDoctorRepository {
        return AddQueueForDoctorRepositoryImpl(addQueueForDoctorApi)
    }

}