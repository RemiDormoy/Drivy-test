package com.rdo.octo.drivytest

interface MainView {
    fun displayCarListNotFound()
    fun displayCarList(cars: List<CarViewModel>)
}
