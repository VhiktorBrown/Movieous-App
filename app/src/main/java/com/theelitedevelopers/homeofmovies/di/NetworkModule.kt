package com.theelitedevelopers.homeofmovies.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.theelitedevelopers.homeofmovies.data.remote.ApiInterface
import com.theelitedevelopers.homeofmovies.utils.Constants
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

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }



    @Provides
    @Singleton
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        okHttpClient.addInterceptor(Interceptor.invoke {
            val original: Request = it.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer "+Constants.ACCESS_TOKEN)
                .header(name = "accept", "application/json")
                .method(original.method, original.body)
                .build()

            return@invoke it.proceed(request)
        })

        return okHttpClient
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60L, TimeUnit.SECONDS)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesGson() : Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun providesRetrofitInstance(
        gson : Gson,
        okHttpClient: OkHttpClient
    ) : ApiInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiInterface::class.java)
    }
}