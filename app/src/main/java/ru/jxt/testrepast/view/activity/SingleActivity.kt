package ru.jxt.testrepast.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.jxt.testrepast.R
import ru.jxt.testrepast.view.fragment.FavoritesFragment
import ru.jxt.testrepast.view.fragment.NearbyRestaurantsFragment
import ru.jxt.testrepast.view.fragment.ProfileFragment

class SingleActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var nearbyRestsFragment: NearbyRestaurantsFragment? = null
    private var favoritesFragment: FavoritesFragment? = null
    private var profileFragment: ProfileFragment? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = when (item.itemId) {
            R.id.navigation_nearby_rests ->
                nearbyRestsFragment ?: (NearbyRestaurantsFragment.newInstance().also { nearbyRestsFragment = it })

            R.id.navigation_favorites ->
                favoritesFragment ?: (FavoritesFragment.newInstance().also { favoritesFragment = it })

            R.id.navigation_profile ->
                profileFragment ?: (ProfileFragment.newInstance().also { profileFragment = it })

            else -> null
        }

        fragment?.apply {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, this)
                .commit()
            return true
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.apply {
            navigation.selectedItemId = getInt(STATE_SELECTED_ITEM_ID)

        } ?: also {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NearbyRestaurantsFragment.newInstance())
                .commit()

            navigation.selectedItemId = R.id.navigation_nearby_rests
        }

        navigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SELECTED_ITEM_ID, navigation.selectedItemId)

        super.onSaveInstanceState(outState)
    }

    companion object {
        const val STATE_SELECTED_ITEM_ID = "stateSelectedItemId"
    }
}
