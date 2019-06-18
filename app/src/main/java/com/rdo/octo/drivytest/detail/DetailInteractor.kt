package com.rdo.octo.drivytest.detail

import com.rdo.octo.drivytest.CarListNotFoundException
import com.rdo.octo.drivytest.cars.Car
import com.rdo.octo.drivytest.cars.CarListRepository
import com.rdo.octo.drivytest.owners.Owner
import javax.inject.Inject

class DetailInteractor @Inject constructor(
    private val repository: CarListRepository
) {

    @Throws(CarNotFoundException::class)
    fun getOwner(name: String): Owner {
        val cars = try {
            repository.getCars()
        } catch (e: CarListNotFoundException) {
            throw CarNotFoundException()
        }
        val car = cars.find { name.startsWith(it.brand) && name.endsWith(it.model) }
        return car?.owner ?: throw CarNotFoundException()
    }
}

class CarNotFoundException : Exception()