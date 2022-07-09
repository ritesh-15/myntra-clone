package com.example.myntra.di

import android.content.Context
import com.example.myntra.common.Constants
import com.example.myntra.common.utils.TokenHandler
import com.example.myntra.data.api.ApiInstance
import com.example.myntra.data.api.authentication.AuthenticationInterface
import com.example.myntra.data.repository.AuthRepositoryImpl
import com.example.myntra.domain.repository.AuthRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val json = GsonBuilder()
            .setLenient()
            .create()

        val headersInterceptor = Interceptor { chain ->
            val tokens = TokenHandler.getTokens(context)

            val request = chain.request().newBuilder()
                .addHeader(Constants.AUTHORIZATION, tokens.accessToken)
                .addHeader(Constants.REFRESH_TOKEN, tokens.refreshToken)
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(headersInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(ApiInstance.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(json))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthenticationInterface {
        return retrofit.create(AuthenticationInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthenticationInterface): AuthRepository {
        return AuthRepositoryImpl(api)
    }
}