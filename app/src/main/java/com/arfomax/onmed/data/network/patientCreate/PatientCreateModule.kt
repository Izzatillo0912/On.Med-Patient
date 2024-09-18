package com.arfomax.onmed.data.network.patientCreate

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.login.LoginRepository
import com.arfomax.onmed.domain.patientCreate.PatientCreateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class PatientCreateModule {

    @Provides
    @Singleton
    fun providePatientCreateApi(retrofit: Retrofit) : PatientCreateApi {
        return retrofit.create(PatientCreateApi :: class.java)
    }

    @Provides
    @Singleton
    fun providePatientCreateRepository(patientCreateApi: PatientCreateApi) : PatientCreateRepository {
        return PatientCreateRepositoryImpl(patientCreateApi)
    }

}