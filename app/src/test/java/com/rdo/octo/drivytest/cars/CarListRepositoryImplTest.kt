package com.rdo.octo.drivytest.cars

import com.rdo.octo.drivytest.CarListNotFoundException
import com.rdo.octo.drivytest.Rating
import com.rdo.octo.drivytest.owners.Owner
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarListRepositoryImplTest {

    private lateinit var server: MockWebServer
    private lateinit var repository: CarListRepositoryImpl

    @Before
    fun setUp() {
        server = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CarListService::class.java)
        repository = CarListRepositoryImpl(service)
    }

    @Test
    fun getCars() {
        // Given
        //language=JSON
        val responseBody = """
[
  {
      "brand": "Citroen",
      "model": "C3",
      "picture_url": "https://raw.githubusercontent.com/drivy/jobs/master/android/api/pictures/13.jpg",
      "price_per_day": 17,
      "rating": {
            "average": 4.697711,
            "count": 259
      },
      "owner": {
         "name": "Elmira Sorrell",
         "picture_url": "https://drivy-assets.imgix.net/jobs/team/howard.jpg",
         "rating": {
              "average": 4.318206,
              "count": 255
            }
        }
   },
   {
      "brand": "Citroen",
      "model": "Xsara Picasso",
      "picture_url": "https://raw.githubusercontent.com/drivy/jobs/master/android/api/pictures/33.jpg",
      "price_per_day": 27,
      "rating": {
        "average": 4.7544947,
        "count": 250
      },
      "owner": {
        "name": "Britni Fitch",
        "picture_url": "https://drivy-assets.imgix.net/jobs/team/sonia.jpg",
        "rating": {
        "average": 4.7478724,
        "count": 107
      }
    }
  }
]
        """.trimIndent()
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        // When
        val result = repository.getCars()

        // Then
        Assertions.assertThat(result).isEqualTo(
            listOf(
                Car(
                    model = "C3",
                    brand = "Citroen",
                    rating = Rating(rate = 4.697711, count = 259),
                    pictureUrl = "https://raw.githubusercontent.com/drivy/jobs/master/android/api/pictures/13.jpg",
                    pricePerDay = 17.0,
                    owner = Owner(
                        name = "Elmira Sorrell",
                        pictureUrl = "https://drivy-assets.imgix.net/jobs/team/howard.jpg",
                        rating = Rating(rate = 4.318206, count = 255)
                    )
                ),
                Car(
                    model = "Xsara Picasso",
                    brand = "Citroen",
                    rating = Rating(rate = 4.7544947, count = 250),
                    pictureUrl = "https://raw.githubusercontent.com/drivy/jobs/master/android/api/pictures/33.jpg",
                    pricePerDay = 27.0,
                    owner = Owner(
                        name = "Britni Fitch",
                        pictureUrl = "https://drivy-assets.imgix.net/jobs/team/sonia.jpg",
                        rating = Rating(rate = 4.7478724, count = 107)
                    )
                )
            )
        )
    }

    @Test(expected = CarListNotFoundException::class)
    fun `repository should throw right error when something wrong occurs`() {
        // Given
        server.enqueue(MockResponse().setResponseCode(400))

        // When
        repository.getCars()
    }
}