package com.appmunki.gigs.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.appmunki.gigs.review.ReviewModel;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;
import com.orm.androrm.field.CharField;
import com.orm.androrm.field.DoubleField;
import com.orm.androrm.field.IntegerField;
import com.orm.androrm.field.OneToManyField;

import java.io.ByteArrayOutputStream;

public class RestaurantModel extends Model {
    protected CharField mTitle;
    protected CharField mDescription;
    protected DoubleField mRating;
    protected IntegerField mVisits;

    protected CharField mPicture;
    protected OneToManyField<RestaurantModel, ReviewModel> mReviews;


    // initializes the standard ID field
    // and sets it to autoincrement
    public RestaurantModel() {
        super(true);
        mTitle = new CharField();
        mDescription = new CharField();
        mPicture = new CharField();
        mRating = new DoubleField();
        mReviews = new OneToManyField<RestaurantModel, ReviewModel>(RestaurantModel.class, ReviewModel.class);
        mVisits = new IntegerField();
    }

    public RestaurantModel(String pTitle, String pDescription, double pRating, int pVisits) {
        this();
        mTitle.set(pTitle);
        mDescription.set(pDescription);
        mRating.set(pRating);
        mVisits.set(0);
    }

    public static final QuerySet<RestaurantModel> readResturants(Context context) {
        return objects(context, RestaurantModel.class);
    }



    public String getTitle() {
        return mTitle.get();
    }

    public void setTitle(String pTitle) {
        this.mTitle.set(pTitle);
    }

    public String getDescription() {
        return mDescription.get();
    }

    public void setDescription(String pDescription) {
        this.mDescription.set(pDescription);
    }

    public double getRating() {
        return mRating.get().doubleValue();
    }

    public void setRating(Double pRating) {
        this.mRating.set(pRating);
    }

    public void setVisits(int pVisit){
        mVisits.set(pVisit);
    }

    public int getVisits(){
        return mVisits.get().intValue();
    }

    public void incrementVisit(){
        int i =mVisits.get().intValue();
        mVisits.set(i++);
    }

    public void decrementVisit(){
        int i =mVisits.get().intValue();
        mVisits.set(i--);
    }
    /**
     * Retrieves the image from the database that is encoded as a Base64 string
     * @return a bitmap of the stored image
     */
    public Bitmap getPicture() {
        byte[] imgbytes = Base64.decode(mPicture.get(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,
                imgbytes.length);
        return bitmap;
    }

    /**
     * Sets the database image asa based 64 string
     * @param pPicture the bitmap of the image being stored
     */
    public void setPicture(Bitmap pPicture) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        this.mPicture.set(encoded);
    }


}
