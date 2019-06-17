package com.rdo.octo.drivytest.owners

import com.rdo.octo.drivytest.Rating

data class Owner(
    val name: String,
    val pictureUrl: String,
    val rating: Rating
)