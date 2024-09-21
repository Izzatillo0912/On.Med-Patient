package com.arfomax.onmed.data.network.deleteQueueForInspection

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.deleteQueueForInspection.DeleteQueueForInspectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class DeleteQueueForInspectionModule {


    @Provides
    @Singleton
    fun provideDeleteQueueForInspectionService(retrofit: Retrofit) : DeleteQueueForInspectionApi {
        return retrofit.create(DeleteQueueForInspectionApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideDeleteQueueForInspectionRepository(
        deleteQueueForInspectionApi: DeleteQueueForInspectionApi
    ): DeleteQueueForInspectionRepository {
        return DeleteQueueForInspectionRepositoryImpl(deleteQueueForInspectionApi)
    }

}