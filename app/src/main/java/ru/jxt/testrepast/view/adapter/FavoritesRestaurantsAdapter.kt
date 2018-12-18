package ru.jxt.testrepast.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.jxt.testrepast.R
import ru.jxt.testrepast.data.model.RestaurantInfoCard
import ru.jxt.testrepast.utils.hide
import ru.jxt.testrepast.utils.show

class FavoritesRestaurantsAdapter(val items: List<RestaurantInfoCard>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mExpandedPosition: Int = -1

    private fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_HEADER -> HeaderViewHolder(parent.inflate(R.layout.header_nearby_restaurant))
        else -> ItemViewHolder(parent.inflate(R.layout.item_favorite_restaurant))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bind(items[position - 1])
    }

    override fun getItemCount() = items.size + 1

    private fun isPositionHeader(position: Int) = position == 0

    override fun getItemViewType(position: Int) = when {
        isPositionHeader(position) -> TYPE_HEADER
        else -> TYPE_ITEM
    }


    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.findViewById<TextView>(R.id.headerFirstTextView).text =
                    view.context.getString(R.string.favorites)

            view.findViewById<TextView>(R.id.headerSecondTextView).text =
                    view.context.getString(R.string.forever_in_your_heart)
        }
    }

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val restaurantNameTextView: TextView = view.findViewById(R.id.restaurantNameTextView)
        private val leaveTipTextView: TextView = view.findViewById(R.id.leaveTipTextView)
        private val waitersListTitleTextView: TextView = view.findViewById(R.id.waitersListTitleTextView)
        private val addressTextView: TextView = view.findViewById(R.id.addressTextView)
        private val descTextView: TextView = view.findViewById(R.id.descTextView)
        private val restaurantCoverImageView: ImageView = view.findViewById(R.id.restaurantCoverImageView)
        private val callImageView: ImageView = view.findViewById(R.id.callImageView)
        private val ratingImageView: ImageView = view.findViewById(R.id.ratingImageView)
        private val expandArrowImageView: ImageView = view.findViewById(R.id.expandArrowImageView)
        private val waitersListLayout: LinearLayout = view.findViewById(R.id.waitersListLayout)

        fun bind(item: RestaurantInfoCard) {
            restaurantNameTextView.text = item.name
            addressTextView.text = item.address
            descTextView.text = item.shortDesc

            val isExpanded = adapterPosition == mExpandedPosition

            expandArrowImageView.rotation = if (isExpanded) 0f else 180f
            view.isActivated = isExpanded
            expandArrowImageView.setOnClickListener {
                mExpandedPosition = if (isExpanded) -1 else adapterPosition
                expandArrowImageView.rotation = (expandArrowImageView.rotation * -1) + 180
                notifyItemChanged(adapterPosition)
            }

            if (isExpanded) {
                descTextView.show()
                leaveTipTextView.show()
                callImageView.show()
                ratingImageView.show()
                waitersListTitleTextView.show()
                waitersListLayout.show()
            } else {
                descTextView.hide()
                leaveTipTextView.hide()
                callImageView.hide()
                ratingImageView.hide()
                waitersListTitleTextView.hide()
                waitersListLayout.hide()
            }

            val drawableId = when (adapterPosition - 1) {
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
    }
}