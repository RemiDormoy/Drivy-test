package com.rdo.octo.drivytest.cars

import com.rdo.octo.drivytest.CarListNotFoundException
import com.rdo.octo.drivytest.Rating
import com.rdo.octo.drivytest.owners.Owner
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

interface CarListRepository {

    @Throws(CarListNotFoundException::class)
    fun getCars(): List<Car>
}

class CarListRepositoryImpl @Inject constructor(
    private val service: CarListService
) : CarListRepository {

    override fun getCars(): List<Car> {
        try {
            val body = service.getCars().execute().body() ?: throw CarListNotFoundException()
            return body.map {
                Car(
                    model = it.model,
                    brand = it.brand,
                    pricePerDay = it.price_per_day,
                    pictureUrl = it.picture_url,
                    owner = it.owner.toOwner(),
                    rating = it.rating.toRating()
                )
            }
        } catch (e: IOException) {
            throw CarListNotFoundException()
        } catch (e: RuntimeException) {
            throw CarListNotFoundException()
        }
    }
}

private fun DrivyOwner.toOwner(): Owner {
    return Owner(name, picture_url, rating.toRating())
}

private fun DrivyRating.toRating(): Rating {
    return Rating(average, count)
}
