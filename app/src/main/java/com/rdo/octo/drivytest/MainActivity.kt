package com.rdo.octo.drivytest

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdo.octo.drivytest.cars.ListModule
import com.rdo.octo.drivytest.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import android.util.Pair as UtilPair

class MainActivity : AppCompatActivity(), MainView {

    private val adapter: CarAdapter by lazy {
        CarAdapter(::onItemClick)
    }

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMainComponent.create()
            .plus(ListModule(this))
            .inject(this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        listRecyclerView.layoutManager = linearLayoutManager
        listRecyclerView.adapter = adapter
        presenter.loadCars()
    }

    private fun onItemClick(carViewModel: CarViewModel, nameView: View, imageView: View, priceTextView: View, ratingCountTextView: View, ratingBar: View) {
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            UtilPair.create(imageView, "image"),
            UtilPair.create(ratingCountTextView, "ratingCount"),
            UtilPair.create(priceTextView, "price"),
            UtilPair.create(nameView, "name"))
        startActivity(DetailActivity.newIntent(this, carViewModel), options.toBundle())
    }

    override fun displayCarListNotFound() {
        listProgressBar.visibility = GONE
        listRecyclerView.visibility = GONE
        errorTextView.visibility = VISIBLE
    }

    override fun displayCarList(cars: List<CarViewModel>) {
        listProgressBar.visibility = GONE
        listRecyclerView.visibility = VISIBLE
        errorTextView.visibility = GONE
        adapter.setList(cars)
    }
}

