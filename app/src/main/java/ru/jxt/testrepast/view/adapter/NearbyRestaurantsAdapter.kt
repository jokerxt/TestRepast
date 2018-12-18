package ru.jxt.testrepast.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.jxt.testrepast.R
import ru.jxt.testrepast.data.model.RestaurantInfoCard
import ru.jxt.testrepast.view.custom.VectorRatingBar

class NearbyRestaurantsAdapter(val items: List<RestaurantInfoCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
        TYPE_HEADER -> HeaderViewHolder(parent.inflate(R.layout.header_nearby_restaurant))
        TYPE_FOOTER -> FooterViewHolder(parent.inflate(R.layout.footer_nearby_restaurant))
        else -> ItemViewHolder(parent.inflate(R.layout.item_nearby_restaurant))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bind(items[position-1])
    }

    override fun getItemCount() = items.size + 2

    private fun isPositionHeader(position: Int) = position == 0

    private fun isPositionFooter(position: Int) = position > items.size

    override fun getItemViewType(position: Int) = when {
        isPositionHeader(position) -> TYPE_HEADER
        isPositionFooter(position) -> TYPE_FOOTER
        else -> TYPE_ITEM
    }


    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val restaurantNameTextView: TextView = view.findViewById(R.id.restaurantNameTextView)
        private val restaurantRatingBar: VectorRatingBar = view.findViewById(R.id.restaurantRatingBar)
        private val restaurantRatingTextView: TextView = view.findViewById(R.id.restaurantRatingTextView)
        private val addressTextView: TextView = view.findViewById(R.id.addressTextView)
        private val descTextView: TextView = view.findViewById(R.id.descTextView)
        private val restaurantCoverImageView: ImageView = view.findViewById(R.id.restaurantCoverImageView)

        fun bind(item: RestaurantInfoCard) {
            restaurantNameTextView.text = item.name
            restaurantRatingBar.rating = item.rating.toFloat()
            restaurantRatingTextView.text = item.rating
            addressTextView.text = item.address
            descTextView.text = item.shortDesc

            val drawableId = when(adapterPosition-1) {
                0 -> R.drawable.test_item_rest_0
                1 -> R.drawable.test_item_rest_1
                2 -> R.drawable.test_item_rest_2
                else -> return
            }

            restaurantCoverImageView.setImageResource(drawableId)
        }
    }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }
}