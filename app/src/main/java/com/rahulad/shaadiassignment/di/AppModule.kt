package com.rahulad.shaadiassignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.rahulad.network.ApiService
import com.rahulad.roomdbpagingdemo.db.AppDatabase
import com.rahulad.roomdbpagingdemo.db.MatchedPersonDao
import com.rahulad.shaadiassignment.application.ShaadiApplication
import com.rahulad.shaadiassignment.network.Url
import com.rahulad.shaadiassignment.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class) // dependency is valid in all application scope
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "matchedDb")
            .build()

    @Provides
    fun providesMatchedPersonDao(db: AppDatabase) : MatchedPersonDao =
        db.matchedPersonDao()

    @Provides
    fun providesDataRepository(matchedDao: MatchedPersonDao): DataRepository =
        DataRepository(matchedDao)

    @Provides
    fun providesBaseUrl(): String = Url.url

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


    @Provides
    @Singleton
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofitBuilder(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}