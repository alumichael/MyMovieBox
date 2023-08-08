package com.homework.mymoviebox.di


import com.homework.mymoviebox.BuildConfig.BASE_URL
import com.homework.mymoviebox.BuildConfig.CERTIFICATE_1
import com.homework.mymoviebox.BuildConfig.CERTIFICATE_2
import com.homework.mymoviebox.BuildConfig.CERTIFICATE_3
import com.homework.mymoviebox.BuildConfig.CERTIFICATE_4
import com.homework.mymoviebox.BuildConfig.DEBUG
import com.homework.mymoviebox.BuildConfig.HOSTNAME
import com.homework.mymoviebox.data.source.remote.network.MyMovieBoxServices
import com.homework.mymoviebox.data.source.remote.network.RemoteInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideCertificatePinner(): CertificatePinner =
        CertificatePinner.Builder()
            .add(HOSTNAME, CERTIFICATE_1)
            .add(HOSTNAME, CERTIFICATE_2)
            .add(HOSTNAME, CERTIFICATE_3)
            .add(HOSTNAME, CERTIFICATE_4)
            .build()

    @Provides
    fun provideOkHttpClient(certificatePinner: CertificatePinner): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(RemoteInterceptor())
            .retryOnConnectionFailure(false)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .certificatePinner(certificatePinner)
            .build()

    @Provides
    fun provideMyMovieBoxService(okHttpClient: OkHttpClient): MyMovieBoxServices =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyMovieBoxServices::class.java)

}