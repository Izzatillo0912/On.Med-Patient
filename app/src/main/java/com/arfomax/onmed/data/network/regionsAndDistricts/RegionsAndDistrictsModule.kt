package com.arfomax.onmed.data.network.regionsAndDistricts

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.regionsAndDistricts.RegionsAndDistrictRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class RegionsAndDistrictsModule {

    @Provides
    @Singleton
    fun provideRegionsAndDistrictsService(retrofit: Retrofit) : RegionsAndDistrictsService {
        return retrofit.create(RegionsAndDistrictsService :: class.java)
    }

    @Provides
    @Singleton
    fun provideRegionsAndDistrictsRepository(regionsAndDistrictsService: RegionsAndDistrictsService) : RegionsAndDistrictRepository {
        return RegionsAndDistrictsRepositoryImpl(regionsAndDistrictsService)
    }

}