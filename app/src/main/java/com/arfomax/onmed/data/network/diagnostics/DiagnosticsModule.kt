package com.arfomax.onmed.data.network.diagnostics

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.diagnostics.DiagnosticsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class DiagnosticsModule {

    @Provides
    @Singleton
    fun provideDiagnosticsApi(retrofit: Retrofit) : DiagnosticsApi {
        return retrofit.create(DiagnosticsApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideDiagnosticsRepository(diagnosticsApi: DiagnosticsApi) : DiagnosticsRepository {
        return DiagnosticsRepositoryImpl(diagnosticsApi)
    }

}