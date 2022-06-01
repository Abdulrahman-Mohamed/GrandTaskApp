package com.abdullrahman.grandtaskapp.di

import android.content.Context
import androidx.room.Room
import com.abdullrahman.grandtaskapp.data.dataSource.api.Api
import com.abdullrahman.grandtaskapp.data.dataSource.room.AppDataBase
import com.abdullrahman.grandtaskapp.data.dataSource.room.UserDao
import com.abdullrahman.grandtaskapp.domain.Constants
import com.abdullrahman.grandtaskapp.repositories.mainRepo.MainRepoImp
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object Module {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            AppDataBase.DatabaseName
        ).build()

    }
    @Provides
    fun ProvideCustomerDao(appDataBase: AppDataBase) = appDataBase.UserDao()

    @Provides
    @ViewModelScoped
    fun provideMainRepo(
        @ApplicationContext appContext: Context,
        api:Api,
        userDao:UserDao
    ):MainRepoImp = MainRepoImp(appContext,api,userDao)
}