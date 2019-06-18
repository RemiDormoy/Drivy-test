package com.rdo.octo.drivytest.detail

import com.rdo.octo.drivytest.owners.Owner
import javax.inject.Inject

interface DetailPresenter {
    fun loadOwner(name: String)
}

class DetailPresenterImpl @Inject constructor(
    private val view: DetailView,
    private val interactor: DetailInteractor
) : DetailPresenter {

    override fun loadOwner(name: String) {
        try {
            val owner = interactor.getOwner(name)
            view.displayOwner(owner.toViewModel())
        } catch (e: CarNotFoundException) {
            view.displayOwnerNotFound()
        }
    }
}

private fun Owner.toViewModel(): OwnerViewModel {
    return OwnerViewModel(name, pictureUrl, rating.rate.toFloat())
}

data class OwnerViewModel(
    val name: String,
    val pictureUrl: String,
    val rating: Float
)

interface DetailView {
    fun displayOwner(viewModel: OwnerViewModel)
    fun displayOwnerNotFound()
}
