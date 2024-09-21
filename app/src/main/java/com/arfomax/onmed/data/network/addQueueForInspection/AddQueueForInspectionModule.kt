package com.arfomax.onmed.data.network.addQueueForInspection

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.addQueueForInspection.AddQueueForInspectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class AddQueueForInspectionModule {


    @Provides
    @Singleton
    fun provideAddQueueForInspectionService(retrofit: Retrofit) : AddQueueForInspectionApi {
        return retrofit.create(AddQueueForInspectionApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideAddQueueForInspectionRepository(
        addQueueForInspectionApi: AddQueueForInspectionApi): AddQueueForInspectionRepository {
        return AddQueueForInspectionRepositoryImpl(addQueueForInspectionApi)
    }

}