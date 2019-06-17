package com.rdo.octo.drivytest

import com.rdo.octo.drivytest.cars.CarListRepository
import java.lang.Exception
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val repository: CarListRepository
) {

    @Throws(CarListNotFoundException::class)
    fun getCars() = repository.getCars().ifEmpty {
        throw CarListNotFoundException()
    }
}

class CarListNotFoundException : Exception()

data class Rating(
    val rate: Double,
    val count: Int
)

