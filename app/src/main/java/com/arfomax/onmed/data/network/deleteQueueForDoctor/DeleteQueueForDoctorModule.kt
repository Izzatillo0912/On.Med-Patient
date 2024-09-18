package com.arfomax.onmed.data.network.deleteQueueForDoctor

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.deleteQueueForDoctor.DeleteQueueForDoctorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class DeleteQueueForDoctorModule {


    @Provides
    @Singleton
    fun provideDeleteQueueForDoctorService(retrofit: Retrofit) : DeleteQueueForDoctorApi {
        return retrofit.create(DeleteQueueForDoctorApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideDeleteQueueForDoctorRepository(deleteQueueForDoctorApi:
                                              DeleteQueueForDoctorApi
    ): DeleteQueueForDoctorRepository {
        return DeleteQueueForDoctorRepositoryImpl(deleteQueueForDoctorApi)
    }

}