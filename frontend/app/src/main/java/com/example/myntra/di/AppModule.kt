package com.example.myntra.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.myntra.common.Constants
import com.example.myntra.data.local.MyDatabase
import com.example.myntra.data.remote.api.ApiInstance
import com.example.myntra.data.remote.api.authentication.AuthenticationInterface
import com.example.myntra.data.remote.api.order.OrderApiInterface
import com.example.myntra.data.remote.api.products.ProductInterface
import com.example.myntra.data.remote.api.user.UserApiInterface
import com.example.myntra.data.repository.*
import com.example.myntra.domain.repository.*
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
    fun provideRetrofit(pref: SharedPreferences): Retrofit {
        val json = GsonBuilder()
            .setLenient()
            .create()

        val headersInterceptor = Interceptor { chain ->
            val accessToken = pref.getString(Constants.ACCESS_TOKEN, null)
            val refreshToken = pref.getString(Constants.REFRESH_TOKEN, null)

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

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext app: Context): MyDatabase {
        return Room.databaseBuilder(app,
            MyDatabase::class.java,
            "myntra_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthenticationInterface {
        return retrofit.create(AuthenticationInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApiInterface {
        return retrofit.create(UserApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApiInterface {
        return retrofit.create(OrderApiInterface::class.java)
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
    fun provideCartRepository(db: MyDatabase): CartRepository {
        return CartRepositoryImpl(db.cartDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserApiInterface, db: MyDatabase): UserRepository {
        return UserRepositoryImpl(api = api, dao = db.userDao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(api: OrderApiInterface): OrderRepository {
        return OrderRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductInterface, db: MyDatabase): ProductRepository {
        return ProductRepositoryImpl(api, db.productDao, db.categoryDao)
    }


}