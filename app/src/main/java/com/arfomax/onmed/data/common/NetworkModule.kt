package com.arfomax.onmed.data.common

import com.arfomax.onmed.presentation.utils.Constants
import com.orhanobut.hawk.Hawk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class NetworkModule {

    @DelicateCoroutinesApi
    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
        Interceptor { chain: Interceptor.Chain ->

            val token : String? = Hawk.get(Constants.TOKEN)

            val request = chain.request().newBuilder().apply {
                if ( token != null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }.build()

            return@Interceptor chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor) : OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}