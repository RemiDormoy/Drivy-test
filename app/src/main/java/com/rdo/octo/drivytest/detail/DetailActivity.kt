package com.rdo.octo.drivytest.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rdo.octo.drivytest.CarViewModel
import com.rdo.octo.drivytest.DaggerMainComponent
import com.rdo.octo.drivytest.R
import com.rdo.octo.drivytest.cars.ListModule
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity(), DetailView {

    companion object {
        private const val EXTRA_IMAGE_URL = "extraImageUrl"
        private const val EXTRA_NAME = "extraName"
        private const val EXTRA_RATING = "extraRating"
        private const val EXTRA_PRICE = "extraPrice"
        private const val EXTRA_RATING_COUNT = "extraRatingCount"

        fun newIntent(context: Context, carViewModel: CarViewModel): Intent {
            return Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_IMAGE_URL, carViewModel.pictureUrl)
                .putExtra(EXTRA_RATING_COUNT, carViewModel.ratingCount)
                .putExtra(EXTRA_RATING, carViewModel.rating)
                .putExtra(EXTRA_PRICE, carViewModel.pricePerDay)
                .putExtra(EXTRA_NAME, carViewModel.name)
        }
    }

    @Inject
    lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        DaggerMainComponent.create()
            .plus(DetailModule(this))
            .inject(this)
        Picasso.get()
            .load(intent.getStringExtra(EXTRA_IMAGE_URL))
            .into(pictureImageView)
        priceTextView.text = intent.getStringExtra(EXTRA_PRICE)
        ratingTextView.text = intent.getStringExtra(EXTRA_RATING_COUNT)
        ratingBar2.rating = intent.getFloatExtra(EXTRA_RATING, 0F)
        intent.getStringExtra(EXTRA_NAME)?.let {
            nameTextView.text = it
            presenter.loadOwner(it)
        }
    }

    override fun displayOwnerNotFound() {
        Toast.makeText(this, "Le propriétaire de cette voiture n'a pas été trouvé", Toast.LENGTH_SHORT).show()
    }

    override fun displayOwner(viewModel: OwnerViewModel) {
        Picasso.get()
            .load(viewModel.pictureUrl)
            .into(ownerImageView)
        ownerRatingBar.rating = viewModel.rating
        ownerNameTextView.text = viewModel.name
        ownerGroup.visibility = VISIBLE
    }
}