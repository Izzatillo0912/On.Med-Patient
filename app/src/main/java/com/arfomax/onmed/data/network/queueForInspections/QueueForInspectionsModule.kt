package com.arfomax.onmed.data.network.queueForInspections

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.queuesForInspections.QueuesForInspectionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class QueueForInspectionsModule {

    @Provides
    @Singleton
    fun provideQueueForInspectionApi(retrofit: Retrofit) : QueueForInspectionsApi {
        return retrofit.create(QueueForInspectionsApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideQueueForInspectionRepository(queueForInspectionsApi: QueueForInspectionsApi):
            QueuesForInspectionsRepository {
        return QueuesForInspectionsRepositoryImpl(queueForInspectionsApi)
    }

}