package com.arfomax.onmed.data.network.queueForDoctor

import com.arfomax.onmed.domain.queuesForDoctor.QueuesForDoctorRepository
import com.arfomax.onmed.data.common.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class QueueForDoctorModule {

    @Provides
    @Singleton
    fun provideQueueForDoctorApi(retrofit: Retrofit) : QueueForDoctorApi {
        return retrofit.create(QueueForDoctorApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideQueueForDoctorRepository(queueForDoctorApi: QueueForDoctorApi): QueuesForDoctorRepository {
        return QueuesForDoctorRepositoryImpl(queueForDoctorApi)
    }

}