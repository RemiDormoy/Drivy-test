package com.rdo.octo.drivytest

import com.rdo.octo.drivytest.cars.ListComponent
import com.rdo.octo.drivytest.cars.ListModule
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [MainModule::class])
interface MainComponent {

    fun plus(module: ListModule): ListComponent
}

@Module
object MainModule {

    @JvmStatic
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/drivy/jobs/master/android/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmStatic
    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}