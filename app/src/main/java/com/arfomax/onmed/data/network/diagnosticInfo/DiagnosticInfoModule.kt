package com.arfomax.onmed.data.network.diagnosticInfo

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.diagnosticInfo.DiagnosticInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class DiagnosticInfoModule {

    @Provides
    @Singleton
    fun provideDDiagnosticInfoApi(retrofit: Retrofit) : DiagnosticInfoApi {
        return retrofit.create(DiagnosticInfoApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideDiagnosticInfoRepository(diagnosticInfoApi: DiagnosticInfoApi) : DiagnosticInfoRepository {
        return DiagnosticInfoRepositoryImpl(diagnosticInfoApi)
    }

}