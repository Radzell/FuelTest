package com.appmunki.gigs.review;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.appmunki.gigs.R;
import com.appmunki.gigs.restaurant.RestaurantDetailFragment;
import com.appmunki.gigs.restaurant.RestaurantModel;

public class ReviewCreateActivity extends Activity implements View.OnClickListener {


    RestaurantModel mRestaurant;
    private EditText mTitleView;
    private EditText mCommentView;
    private RatingBar mRatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(
                new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        if (!getIntent().hasExtra(RestaurantDetailFragment.ARG_RESTAURANT_ID))
            finish();

        mRestaurant = RestaurantModel.readRestaurants(this).get(getIntent().getExtras().getInt(RestaurantDetailFragment.ARG_RESTAURANT_ID));


        mTitleView = (EditText) findViewById(R.id.titleeditText);
        mCommentView = (EditText) findViewById(R.id.commenteditText);
        mRatingView = (RatingBar) findViewById(R.id.ratingBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_review_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_done:
                saveReview();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveReview() {
        // Reset errors.
        mTitleView.setError(null);
        mCommentView.setError(null);

        // Store values at the time of the login attempt.
        String mTitle = mTitleView.getText().toString();
        String mComment = mCommentView.getText().toString();
        double mRating = mRatingView.getRating();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mTitle)) {
            mTitleView.setError(getString(R.string.error_field_required));
            focusView = mTitleView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mComment)) {
            mCommentView.setError(getString(R.string.error_field_required));
            focusView = mCommentView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt save and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            ReviewModel reviewModel = new ReviewModel(mTitle, mComment, mRating);
            reviewModel.setReviewedRestaurant(mRestaurant);

            reviewModel.save(this);

            mRestaurant.setRating((reviewModel.getRating()+mRestaurant.getRating())/((double)mRestaurant.getReviewCount(this)+1));
            mRestaurant.save(this);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
