package com.arfomax.onmed.data.network.smsVerification

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.smsVerification.SmsVerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class SmsVerificationModule {


    @Provides
    @Singleton
    fun provideSmsVerificationService(retrofit: Retrofit) : SmsVerificationApi {
        return retrofit.create(SmsVerificationApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideSmsVerificationRepository(smsVerificationApi: SmsVerificationApi) : SmsVerificationRepository {
        return SmsVerificationRepositoryImpl(smsVerificationApi)
    }

}