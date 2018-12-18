package ru.jxt.testrepast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_nearby_restaurants.*
import ru.jxt.testrepast.R
import ru.jxt.testrepast.data.model.RestaurantInfoCard
import ru.jxt.testrepast.view.adapter.NearbyRestaurantsAdapter

class NearbyRestaurantsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nearby_restaurants, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val items = mutableListOf<RestaurantInfoCard>()

        items += RestaurantInfoCard("Maecenas 1903",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "4.2",
            "89009009999",
            "Екатеринбург, ул. Братьев Быковых, 74",
            "http://123.ru/image.png")

        items += RestaurantInfoCard("Suspendisse Potenti",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "3.8",
            "89009009999",
            "Екатеринбург, ул. Ленина, 7",
            "http://123.ru/image.png")

        items += RestaurantInfoCard("Olene Milano",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            "4.9",
            "89009009999",
            "Екатеринбург, ул. Ленина, 7",
            "http://123.ru/image.png")

        nearbyRestaurantsRecyclerView.layoutManager = LinearLayoutManager(context)
        nearbyRestaurantsRecyclerView.adapter = NearbyRestaurantsAdapter(items)
    }

    companion object {
        @JvmStatic
        fun newInstance() = NearbyRestaurantsFragment()
    }
}