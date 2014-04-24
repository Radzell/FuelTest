package com.appmunki.gigs.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.appmunki.gigs.R;

public class RestaurantCreateActivity extends Activity implements View.OnClickListener {


    private static final int SELECT_PICTURE = 1;
    private EditText mTitleView;
    private EditText mDescriptionView;
    private RatingBar mRatingView;
    private Button mButtonView;
    private Bitmap mBitmap;

    /**
     * Initialize the views and the event drivers
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        mTitleView = (EditText) findViewById(R.id.titleeditText);
        mDescriptionView = (EditText) findViewById(R.id.descriptioneditText);
        mRatingView = (RatingBar) findViewById(R.id.ratingBar);
        mButtonView = (Button) findViewById(R.id.imageButton);
        mButtonView.setOnClickListener(this);

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
            case R.id.action_done:
                saveRestaurant();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Saves the restaurant data
     * TODO: implement a asynchronous version
     */
    private void saveRestaurant() {
        // Reset errors.
        mTitleView.setError(null);
        mDescriptionView.setError(null);

        // Store values at the time of the attempt
        String mTitle = mTitleView.getText().toString();
        String mDescription = mDescriptionView.getText().toString();
        double mRating = mRatingView.getRating();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid title.
        if (TextUtils.isEmpty(mTitle)) {
            mTitleView.setError(getString(R.string.error_field_required));
            focusView = mTitleView;
            cancel = true;
        }

        // Check for a valid description.
        if (TextUtils.isEmpty(mDescription)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt save and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            //finish and save restaurant in database
            RestaurantModel restaurantModel = new RestaurantModel(mTitle, mDescription, mRating, 0);
            restaurantModel.setPicture(mBitmap);
            restaurantModel.save(this);

            finish();
        }
    }

    /**
     * Retrieve the image and camera the save the bitmap and set the imageview.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 19) {
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        mButtonView.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmap));

                    } catch (Exception e) {
                    }


                } else {
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        parcelFileDescriptor.close();

                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        mButtonView.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmap));

                    } catch (Exception e) {

                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }
}
