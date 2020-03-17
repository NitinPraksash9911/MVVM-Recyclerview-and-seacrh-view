package com.example.sampleappmovielist.network

import com.example.sampleappmovielist.BuildConfig
import com.example.sampleappmovielist.MyApplication
import com.example.sampleappmovielist.repository.MovieListWebServices
import com.example.sampleappmovielist.utils.AppContants.Companion.HEADER_CACHE_CONTROL
import com.example.sampleappmovielist.utils.AppContants.Companion.HEADER_PRAGMA
import com.example.sampleappmovielist.utils.AppContants.Companion.cacheSize
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


object NetworkModule {


    private val cache = Cache(File(MyApplication.instance?.cacheDir, "movieListCache"), cacheSize)


    private var webService: MovieListWebServices? = null
    //---method return PostService
    fun getPostService(): MovieListWebServices {
        if (webService == null) {
            /* create retrofit object **/
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            webService = retrofit.create(MovieListWebServices::class.java)
        }
        return webService!!
    }

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor()!!)
                .addNetworkInterceptor(networkInterceptor)
                .addInterceptor(offlineInterceptor)
                .build()
        }


    private val networkInterceptor: Interceptor
        get() {
            return Interceptor {
                val response = it.proceed(it.request())
                val cacheControl = CacheControl.Builder()
                    .maxAge(3, TimeUnit.SECONDS)
                    .build()
                response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()

            }
        }

    private val offlineInterceptor: Interceptor
        get() {
            return Interceptor {

                var request = it.request()

                if (!MyApplication.hasNetwork()) {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(1, TimeUnit.MINUTES)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                return@Interceptor it.proceed(request);
            }
        }



    private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                }
            })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }


}