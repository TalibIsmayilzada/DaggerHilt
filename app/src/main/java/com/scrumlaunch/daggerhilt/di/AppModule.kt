package com.scrumlaunch.daggerhilt.di

import com.scrumlaunch.daggerhilt.BuildConfig
import com.scrumlaunch.daggerhilt.data.remote.GithubAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TIMEOUT_SEC = 60L

    //Key names
    private const val USER_AGENT_KEY = "User-Agent"
    private const val ACCEPT_KEY = "Accept"

    //Key values
    private const val USER_AGENT_VALUE = "Android"
    private const val ACCEPT_VALUE = "application/json"

    @Provides
    @Singleton
    fun provideLogginInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header(USER_AGENT_KEY, USER_AGENT_VALUE)
                    .header(ACCEPT_KEY, ACCEPT_VALUE)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
        return okHttpClient.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubAPI = retrofit.create(GithubAPI::class.java)
}