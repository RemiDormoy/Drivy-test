package com.rdo.octo.drivytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rdo.octo.drivytest.cars.ListModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private val adapter: CarAdapter by lazy {
        CarAdapter()
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

