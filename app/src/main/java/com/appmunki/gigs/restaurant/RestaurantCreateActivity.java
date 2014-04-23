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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.appmunki.gigs.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RestaurantCreateActivity extends Activity {


    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;

    RestaurantModel mRestaurant;
    private EditText mTitleView;
    private EditText mDescriptionView;
    private RatingBar mRatingView;
    private String mTitle;
    private String mDescription;
    private double mRating;
    private Button mButtonView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant_view);


        mTitleView = (EditText) findViewById(R.id.titleeditText);
        mDescriptionView = (EditText) findViewById(R.id.descriptioneditText);
        mRatingView = (RatingBar) findViewById(R.id.ratingBar);
        mButtonView = (Button) findViewById(R.id.imageButton);
        mButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });

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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveRestaurant() {
        // Reset errors.
        mTitleView.setError(null);
        mDescriptionView.setError(null);

        // Store values at the time of the login attempt.
        mTitle = mTitleView.getText().toString();
        mDescription = mDescriptionView.getText().toString();
        mRating = mRatingView.getRating();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mTitle)) {
            mTitleView.setError(getString(R.string.error_field_required));
            focusView = mTitleView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mDescription)) {
            mDescriptionView.setError(getString(R.string.error_field_required));
            focusView = mDescriptionView;
            cancel = true;
        }

        if(mBitmap==null){
            focusView = mButtonView;
            cancel=true;
        }

        if (cancel) {
            // There was an error; don't attempt save and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Log.i("TAG","bitmaps: "+mBitmap);
            RestaurantModel restaurantModel = new RestaurantModel(mTitle,mDescription,mRating,0);
            restaurantModel.setPicture(mBitmap);
            restaurantModel.save(this,RestaurantModel.readResturants(this).all().count()+20);

            finish();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 19) {
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        mButtonView.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmap));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        parcelFileDescriptor.close();

                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                        mButtonView.setBackgroundDrawable(new BitmapDrawable(getResources(), mBitmap));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }






}
