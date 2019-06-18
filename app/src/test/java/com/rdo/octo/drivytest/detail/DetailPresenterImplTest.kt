package com.rdo.octo.drivytest.detail

import com.rdo.octo.drivytest.Rating
import com.rdo.octo.drivytest.owners.Owner
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailPresenterImplTest {

    @Mock
    private lateinit var view: DetailView
    @Mock
    private lateinit var interactor: DetailInteractor
    @InjectMocks
    private lateinit var presenter: DetailPresenterImpl

    @Test
    fun loadOwner() {
        // Given
        given(interactor.getOwner("name")).willReturn(Owner("ownerName", "pictureUrl", Rating(12.0, 31)))

        // When
        presenter.loadOwner("name")

        // Then
        then(view).should(Mockito.only()).displayOwner(OwnerViewModel("ownerName", "pictureUrl", 12f))
    }

    @Test
    fun `loadOwner should call displayOwnerNotFound when interactor throws exception`() {
        // Given
        given(interactor.getOwner("name")).willThrow(CarNotFoundException())

        // When
        presenter.loadOwner("name")

        // Then
        then(view).should(Mockito.only()).displayOwnerNotFound()
    }
}