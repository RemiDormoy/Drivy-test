package com.rdo.octo.drivytest

import com.rdo.octo.drivytest.cars.Car
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val interactor: MainInteractor
) : MainPresenter {

    override fun loadCars() {
        try {
            val cars = interactor.getCars()
            view.displayCarList(cars.toViewModels())
        } catch (e: CarListNotFoundException) {
            view.displayCarListNotFound()
        }
    }
}

private fun List<Car>.toViewModels() = map {
    CarViewModel(
        name = listOf(it.brand, it.model).joinToString(" "),
        pictureUrl = it.pictureUrl,
        pricePerDay = "${it.pricePerDay} €/j",
        rating = it.rating.rate.toFloat(),
        ratingCount = it.rating.count.toString()
    )
}
