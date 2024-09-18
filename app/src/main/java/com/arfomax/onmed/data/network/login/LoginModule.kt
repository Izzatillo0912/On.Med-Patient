package com.arfomax.onmed.data.network.login

import com.arfomax.onmed.data.common.NetworkModule
import com.arfomax.onmed.domain.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule :: class])
@InstallIn(SingletonComponent :: class)
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit) : LoginApi {
        return retrofit.create(LoginApi :: class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(loginApi: LoginApi) : LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }

}