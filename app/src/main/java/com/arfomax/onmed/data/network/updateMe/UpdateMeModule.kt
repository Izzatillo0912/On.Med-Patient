package com.arfomax.onmed.data.network.updateMe

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.updateMe.UpdateMeRepository
import com.arfomax.onmed.domain.updateQueueDateForDoctor.UpdateQueueDateForDoctorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class UpdateMeModule {


    @Provides
    @Singleton
    fun provideUpdateMeApi(retrofit: Retrofit) : UpdateMeApi {
        return retrofit.create(UpdateMeApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideUpdateMeRepository(updateMeApi: UpdateMeApi): UpdateMeRepository {
        return UpdateMeRepositoryImpl(updateMeApi)
    }

}