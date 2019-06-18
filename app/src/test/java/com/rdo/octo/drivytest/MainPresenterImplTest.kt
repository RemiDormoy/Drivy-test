package com.rdo.octo.drivytest

import com.rdo.octo.drivytest.cars.Car
import com.rdo.octo.drivytest.owners.Owner
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterImplTest {

    @Mock
    private lateinit var view: MainView
    @Mock
    private lateinit var interactor: MainInteractor
    @InjectMocks
    private lateinit var presenterImpl: MainPresenterImpl

    @Test
    fun loadCars() {
        // Given
        val car = Car(
            brand = "brand",
            model = "model",
            rating = Rating(3.7, 30),
            owner = mock(Owner::class.java),
            pictureUrl = "picture.com",
            pricePerDay = 12.0
        )
        given(interactor.getCars()).willReturn(listOf(car))

        // When
        presenterImpl.loadCars()

        // Then
        then(view).should(only()).displayCarList(listOf(
            CarViewModel(
                name = "brand model",
                pictureUrl = "picture.com",
                pricePerDay = "12.0 €/j",
                rating = 3.7f,
                ratingCount = "30"
            )))
    }

    @Test
    fun `loadCars should display list not found when interactor throw error`() {
        // Given
        given(interactor.getCars()).willThrow(CarListNotFoundException())

        // When
        presenterImpl.loadCars()

        // Then
        then(view).should(only()).displayCarListNotFound()
    }
}