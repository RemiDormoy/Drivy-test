package com.rdo.octo.drivytest.cars

import android.media.Rating
import retrofit2.Call
import retrofit2.http.GET

interface CarListService {

    @GET("cars.json")
    fun getCars() : Call<List<DrivyCar>>
}

data class DrivyCar(
    val brand: String,
    val model: String,
    val picture_url: String,
    val price_per_day: Double,
    val rating: DrivyRating,
    val owner: DrivyOwner
)

data class DrivyOwner(
    val name: String,
    val rating: DrivyRating,
    val picture_url: String
)

data class DrivyRating(
    val average: Double,
    val count: Int
)