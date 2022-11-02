package com.dev.jocey.testgiphy.di


import com.dev.jocey.testgiphy.BuildConfig
import com.dev.jocey.testgiphy.data.remote.ApiGiphy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor() = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .url(
                chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
            )
            .build()
        return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor) =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiGiphy = retrofit.create(ApiGiphy::class.java)
}