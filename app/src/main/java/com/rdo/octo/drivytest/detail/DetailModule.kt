package com.rdo.octo.drivytest.detail

import com.rdo.octo.drivytest.*
import com.rdo.octo.drivytest.cars.CarListRepository
import com.rdo.octo.drivytest.cars.CarListRepositoryImpl
import com.rdo.octo.drivytest.cars.CarListService
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import kotlin.concurrent.thread

@LocalScope
@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    fun inject(activity: DetailActivity)
}


@Module
class DetailModule(private val activity: DetailActivity) {

    @Provides
    fun providesPresenter(presenterImpl: DetailPresenterImpl): DetailPresenter = object : DetailPresenter {
        override fun loadOwner(name: String) {
            thread {
                presenterImpl.loadOwner(name)
            }
        }
    }

    @Provides
    fun providesView(): DetailView = object : DetailView {

        override fun displayOwner(viewModel: OwnerViewModel) {
            GlobalScope.launch(Dispatchers.Main) {
                activity.displayOwner(viewModel)
            }
        }

        override fun displayOwnerNotFound() {
            GlobalScope.launch(Dispatchers.Main) {
                activity.displayOwnerNotFound()
            }
        }
    }

    @Provides
    fun providesRepository(repositoryImpl: CarListRepositoryImpl): CarListRepository = repositoryImpl

    @Provides
    fun providesCarListService(retrofit: Retrofit): CarListService =
        retrofit.create(CarListService::class.java)
}