package com.arfomax.onmed.data.network.myQueuesForDiagnostics


import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.myQueueusForDiagnostics.MyQueuesForDiagnosticsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class MyQueueForDiagnosticsModule {

    @Provides
    @Singleton
    fun provideMyQueueForDiagnosticsApi(retrofit: Retrofit) : MyQueuesForDiagnosticsApi {
        return retrofit.create(MyQueuesForDiagnosticsApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideMyQueueForDiagnosticsRepository(
        myQueuesForDiagnosticsApi: MyQueuesForDiagnosticsApi): MyQueuesForDiagnosticsRepository {
        return MyQueuesForDiagnosticsRepositoryImpl(myQueuesForDiagnosticsApi)
    }

}