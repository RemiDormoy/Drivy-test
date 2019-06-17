package com.rdo.octo.drivytest.cars

import com.rdo.octo.drivytest.owners.Owner
import com.rdo.octo.drivytest.Rating

data class Car(
    val model: String,
    val brand: String,
    val rating: Rating,
    val pictureUrl: String,
    val pricePerDay: Double,
    val owner: Owner
)