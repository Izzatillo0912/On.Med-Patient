package com.arfomax.onmed.data.network.doctors

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.doctors.DoctorsRepository
import com.arfomax.onmed.domain.regionsAndDistricts.RegionsAndDistrictRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class DoctorsModule {

    @Provides
    @Singleton
    fun provideDoctorsApi(retrofit: Retrofit) : DoctorsApi {
        return retrofit.create(DoctorsApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideDoctorsRepository(doctorsApi: DoctorsApi) : DoctorsRepository {
        return DoctorsRepositoryImpl(doctorsApi)
    }

}