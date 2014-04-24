package com.appmunki.gigs.restaurant;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appmunki.gigs.R;
import com.appmunki.gigs.review.ReviewCreateActivity;

/**
 * An activity representing a single Restaurant detail screen. This activity is
 * only used on handset devices. On tablet-size devices, item details are
 * presented side-by-side with a list of items in a
 * {@link RestaurantListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link RestaurantDetailFragment}.
 */
public class RestaurantDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(
                    RestaurantDetailFragment.ARG_RESTAURANT_ID,
                    getIntent().getIntExtra(
                            RestaurantDetailFragment.ARG_RESTAURANT_ID, -1)
            );
            RestaurantDetailFragment fragment = new RestaurantDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.restaurant_detail_container, fragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.write_review_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Starts the activity to write a review
     */
    private void writeReview() {
        Intent intent = new Intent(this, ReviewCreateActivity.class);
        intent.putExtra(RestaurantDetailFragment.ARG_RESTAURANT_ID, getIntent().getIntExtra(
                RestaurantDetailFragment.ARG_RESTAURANT_ID, -1));

        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_write_review:
                writeReview();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
