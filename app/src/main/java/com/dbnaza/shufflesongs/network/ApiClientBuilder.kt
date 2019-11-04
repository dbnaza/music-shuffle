package com.dbnaza.shufflesongs.network

import com.dbnaza.shufflesongs.BuildConfig
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientBuilder {
    companion object {
        private const val DEFAULT_READ_TIMEOUT = 15000L
        private const val DEFAULT_CONNECT_TIMEOUT = 5L

        fun <S> createService(
            serviceClass: Class<S>,
            baseUrl: String,
            readTimeoutInMills: Long = DEFAULT_READ_TIMEOUT,
            gson: Gson = Gson(),
            vararg interceptors: Interceptor
        ): S {

            val httpClientBuilder = OkHttpClient.Builder()

            interceptors.forEach { interceptor -> httpClientBuilder.addInterceptor(interceptor) }

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClientBuilder.addInterceptor(logging)
            }

            val client = httpClientBuilder
                    .readTimeout(readTimeoutInMills, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .build()
            val retrofit = getClientBuilder(baseUrl, gson)
                    .client(client)
                    .build()
            return retrofit.create(serviceClass)
        }

        private fun getClientBuilder(baseUrl: String, gson: Gson): Retrofit.Builder {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
        }
    }

}