package com.appmunki.gigs.restaurant;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appmunki.gigs.R;

/**
 * An activity representing a list of Resturants. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link RestaurantDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link RestaurantListFragment} and the item details (if present) is a
 * {@link RestaurantDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link RestaurantListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class RestaurantListActivity extends Activity implements
        RestaurantListFragment.Callbacks, ActionBar.OnNavigationListener {

    String TAG = this.getClass().getSimpleName();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_grid);


        // Standard tabbed navigation setup.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(false);
        //actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbartitle, null));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_grid_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int filter = 0;
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_all:
                filter = 0;
                break;
            case R.id.action_most_visited:
                filter = 1;
                break;
            case R.id.action_highly_Rated:
                filter = 2;
                break;
            case R.id.action_add_resturant:
                Intent intent = new Intent(this, RestaurantCreateActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        // Handle presses on the action bar items
        RestaurantListFragment gridFragment = (RestaurantListFragment) getFragmentManager().findFragmentById(R.id.resturant_grid);
        gridFragment.filterRestaurants(filter);

        return true;

    }

    /**
     * Callback method from {@link RestaurantListFragment.Callbacks} indicating
     * that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int id) {
        Log.i(TAG, "Item clicked");

        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(this,
                RestaurantDetailActivity.class);
        detailIntent.putExtra(RestaurantDetailFragment.ARG_RESTAURANT_ID, id);
        startActivity(detailIntent);

    }


    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        RestaurantListFragment gridFragment = (RestaurantListFragment) getFragmentManager().findFragmentById(R.id.resturant_grid);

        gridFragment.filterRestaurants(position);
        return false;
    }
}
