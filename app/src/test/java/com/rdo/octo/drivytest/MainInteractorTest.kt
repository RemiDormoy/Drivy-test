package com.rdo.octo.drivytest

import com.rdo.octo.drivytest.cars.Car
import com.rdo.octo.drivytest.cars.CarListRepository
import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.mock
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainInteractorTest {

    @Mock
    private lateinit var repository: CarListRepository
    @InjectMocks
    private lateinit var interactor: MainInteractor

    @Test
    fun getCars() {
        // Given
        val car = mock(Car::class.java)
        given(repository.getCars()).willReturn(listOf(car))

        // When
        val result = interactor.getCars()

        // Then
        Assertions.assertThat(result).isEqualTo(listOf(car))
    }

    @Test(expected = CarListNotFoundException::class)
    fun `getCars should throw error when list is empty`() {
        // Given
        given(repository.getCars()).willReturn(emptyList())

        // When
        interactor.getCars()
    }

    @Test(expected = CarListNotFoundException::class)
    fun `getCars should throw error repository throw error`() {
        // Given
        given(repository.getCars()).willThrow(CarListNotFoundException())

        // When
        interactor.getCars()
    }
}