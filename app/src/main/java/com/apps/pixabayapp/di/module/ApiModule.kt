package com.apps.pixabayapp.di.module

import android.util.Log
import com.apps.pixabayapp.MyApplication
import com.apps.pixabayapp.data.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object ApiModule {

    private const val BASE_URL = "https://pixabay.com/"
    private const val TAG = "ApiService"
    const val HEADER_CACHE_CONTROL = "Cache-Control"
    const val HEADER_PRAGMA = "Pragma"

    private const val cacheSize = 10 * 1024 * 1024 // 5 MB
        .toLong()

    @Provides
    @Singleton
    fun getApiInterface(retroFit: Retrofit): ApiService {
        return retroFit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient? {
        return OkHttpClient.Builder()
            .cache(cache())
            .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor()) // only used when network is on
            .addInterceptor(offlineInterceptor())
            .addInterceptor(addQueryParameter())
            .build()
    }

    @Provides
    @Singleton
    fun addQueryParameter(): Interceptor {
        return Interceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", "16493408-1f01543c145d09e4208784e1f")
                .build()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    //val apiService: ApiService = getRetrofit()
    //    .create(ApiService::class.java)

    @Provides
    @Singleton
    fun cache(): Cache? {
        return Cache(
            File(MyApplication.instance?.cacheDir, "someIdentifier"),
            cacheSize
        )
    }


    @Provides
    @Singleton
    fun offlineInterceptor(): Interceptor? {
        return Interceptor { chain ->
            Log.d(TAG, "offline interceptor: called.")
            var request: Request = chain.request()

            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (!MyApplication.hasNetwork()) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    fun networkInterceptor(): Interceptor? {
        return Interceptor { chain ->
            Log.d(TAG, "network interceptor: called.")
            val response: Response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(5, TimeUnit.SECONDS)
                .build()
            response.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Log.d(
                    TAG,
                    "log: http log: $message"
                )
            })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

}