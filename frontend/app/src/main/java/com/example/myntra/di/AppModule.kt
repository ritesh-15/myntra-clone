package com.example.myntra.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.myntra.common.Constants
import com.example.myntra.common.utils.TokenHandler
import com.example.myntra.data.api.ApiInstance
import com.example.myntra.data.api.authentication.AuthenticationInterface
import com.example.myntra.data.api.products.ProductInterface
import com.example.myntra.data.repository.AuthRepositoryImpl
import com.example.myntra.data.repository.ProductRepositoryImpl
import com.example.myntra.domain.repository.AuthRepository
import com.example.myntra.domain.repository.ProductRepository
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
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context, pref: SharedPreferences): Retrofit {
        val json = GsonBuilder()
            .setLenient()
            .create()

        val headersInterceptor = Interceptor { chain ->
            val accessToken = pref.getString(Constants.ACCESS_TOKEN, null)
            val refreshToken = pref.getString(Constants.REFRESH_TOKEN, null)

            Log.d("ACCESS_TOKEN", accessToken ?: "null")
            Log.d("REFRESH_TOKEN", refreshToken ?: "null")

            val request = chain.request().newBuilder()
                .addHeader(Constants.AUTHORIZATION, "Bearer ${accessToken ?: ""}")
                .addHeader("refreshtoken", "Bearer ${refreshToken ?: ""}")
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

    @Provides
    @Singleton
    fun providesProductApi(retrofit: Retrofit): ProductInterface {
        return retrofit.create(ProductInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductInterface): ProductRepository {
        return ProductRepositoryImpl(api)
    }
}