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
//first we will create a Network Module Class then annotate it with @Module and @InstallIn and pass SingletonComponent::class bcos it will single object in our project
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
    fun providesTweetsyAPI(retrofit: Retrofit): WebApi {
        return retrofit.create(WebApi::class.java)

    }
}