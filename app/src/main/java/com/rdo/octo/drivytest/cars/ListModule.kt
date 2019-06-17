package com.rdo.octo.drivytest.cars

import com.rdo.octo.drivytest.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import kotlin.concurrent.thread

@LocalScope
@Subcomponent(modules = [ListModule::class])
interface ListComponent {

    fun inject(activity: MainActivity)
}


@Module
class ListModule(private val activity: MainActivity) {

    @Provides
    fun providesPresenter(presenterImpl: MainPresenterImpl): MainPresenter = object : MainPresenter {
        override fun loadCars() {
            thread {
                presenterImpl.loadCars()
            }
        }
    }

    @Provides
    fun providesView(): MainView = object : MainView {

        override fun displayCarListNotFound() {
            GlobalScope.launch(Dispatchers.Main) {
                activity.displayCarListNotFound()
            }
        }

        override fun displayCarList(cars: List<CarViewModel>) {
            GlobalScope.launch(Dispatchers.Main) {
                activity.displayCarList(cars)
            }
        }
    }

    @Provides
    fun providesRepository(repositoryImpl: CarListRepositoryImpl): CarListRepository = repositoryImpl

    @Provides
    fun providesCarListService(retrofit: Retrofit): CarListService =
        retrofit.create(CarListService::class.java)
}