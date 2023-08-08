package com.homework.mymoviebox.data.source.remote.network

import com.homework.mymoviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RemoteInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}