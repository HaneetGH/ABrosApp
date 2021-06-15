package com.technorapper.hiltsample.di

import android.content.Context

import com.google.gson.Gson
import com.technorapper.hiltsample.data.SynchronousApi
import com.technorapper.hiltsample.data.UserPreferences
import com.technorapper.hiltsample.di.scope.ApplicationScoped
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASEURL = "https://www.website.com"



    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(1200, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                requestBuilder.header("x-hasura-admin-secret", "incredible-admin-secret")
                chain.proceed(requestBuilder.build())
            }
            .connectTimeout(1200, TimeUnit.SECONDS)
            .build()

    }

    @Provides
    @Singleton
    fun provideService(
        appPrefs: UserPreferences,
        okHttpClient: OkHttpClient
    ): SynchronousApi {


        return Retrofit.Builder().baseUrl(BASEURL)
            /* .addConverterFactory(MoshiConverterFactory.create())*/
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(SynchronousApi::class.java)

    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .build()

        return@Interceptor chain.proceed(request)
    }


}