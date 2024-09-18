package com.arfomax.onmed.data.network.getWorkDays

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.getWorkDays.GetWorkDaysRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class GetWorkDaysModule {

    @Provides
    @Singleton
    fun provideWorkDaysApi(retrofit: Retrofit) : GetWorkDaysApi {
        return retrofit.create(GetWorkDaysApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideWorkDaysRepository(getWorkDaysApi: GetWorkDaysApi) : GetWorkDaysRepository {
        return GetWorkDaysRepositoryImpl(getWorkDaysApi)
    }

}