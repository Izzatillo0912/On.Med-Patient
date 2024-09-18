package com.arfomax.onmed.data.network.myQueuesForDoctors


import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.myQueueusForDoctors.MyQueuesForDoctorsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class MyQueueForDoctorsModule {

    @Provides
    @Singleton
    fun provideMyQueuesForDoctorsApi(retrofit: Retrofit) : MyQueuesForDoctorsApi {
        return retrofit.create(MyQueuesForDoctorsApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideMyQueuesForDoctorsRepository(myQueuesForDoctorsApi: MyQueuesForDoctorsApi): MyQueuesForDoctorsRepository {
        return MyQueuesForDoctorRepositoryImpl(myQueuesForDoctorsApi)
    }

}