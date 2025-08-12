package sszj.s.adblockerapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sszj.s.adblockerapp.api.WebApi
import javax.inject.Singleton

//4 create DI
@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    //call interface here
    @Singleton
    @Provides
    fun providesWebAPI(retrofit: Retrofit): WebApi {
        return retrofit.create(WebApi::class.java)

    }
}