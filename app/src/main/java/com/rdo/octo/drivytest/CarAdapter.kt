package com.rdo.octo.drivytest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_car.view.*

class CarAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cars = emptyList<CarViewModel>()

    fun setList(cars: List<CarViewModel>) {
        this.cars = cars
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_car,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(
            view
        ) {}
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val car = cars[position]
        with(holder.itemView) {
            Picasso.get()
                .load(car.pictureUrl)
                .into(carImageView)
            nameTextView.text = car.name
            priceTextView.text = car.pricePerDay
            ratingCountTextView.text = car.ratingCount
            ratingBar.rating = car.rating
        }
    }

}