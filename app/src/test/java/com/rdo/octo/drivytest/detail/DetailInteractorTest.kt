package com.rdo.octo.drivytest.detail

import com.rdo.octo.drivytest.CarListNotFoundException
import com.rdo.octo.drivytest.Rating
import com.rdo.octo.drivytest.cars.Car
import com.rdo.octo.drivytest.cars.CarListRepository
import com.rdo.octo.drivytest.owners.Owner
import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.mock
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailInteractorTest {

    @Mock
    private lateinit var repository: CarListRepository
    @InjectMocks
    private lateinit var interactor: DetailInteractor

    @Test
    fun getOwner() {
        // Given
        val owner = Mockito.mock(Owner::class.java)
        val car1 = dummyCar(brand = "Citroen", model = "Picasso")
        val car2 = dummyCar(brand = "Renault", model = "C4")
        val car3 = dummyCar(brand = "Citroen", model = "C4", owner = owner)
        given(repository.getCars()).willReturn(listOf(car1, car2, car3))

        // When
        val result = interactor.getOwner("Citroen C4")

        // Then
        Assertions.assertThat(result).isEqualTo(owner)
    }

    @Test(expected = CarNotFoundException::class)
    fun `getOwner should throw exception when there is no car matching name`() {
        // Given
        val owner = Mockito.mock(Owner::class.java)
        val car1 = dummyCar(brand = "Citroen", model = "Picasso")
        val car2 = dummyCar(brand = "Renault", model = "C4")
        given(repository.getCars()).willReturn(listOf(car1, car2))

        // When
        interactor.getOwner("Citroen C4")
    }

    @Test(expected = CarNotFoundException::class)
    fun `getOwner should throw right exception when repository throws exception`() {
        // Given
        given(repository.getCars()).willThrow(CarListNotFoundException())

        // When
        interactor.getOwner("Citroen C4")
    }

    private fun dummyCar(
        model: String = "model",
        brand: String = "brand",
        rating: Rating = Rating(0.0, 0),
        pictureUrl: String = "pictureUrl",
        pricePerDay: Double = 0.0,
        owner: Owner = Owner("name", "pictureUrl", Rating(0.0, 0))
    ): Car {
        return Car(model, brand, rating, pictureUrl, pricePerDay, owner)
    }
}