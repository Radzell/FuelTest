package com.appmunki.gigs.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.appmunki.gigs.review.ReviewModel;
import com.orm.androrm.Model;
import com.orm.androrm.QuerySet;
import com.orm.androrm.field.CharField;
import com.orm.androrm.field.DoubleField;
import com.orm.androrm.field.IntegerField;
import com.orm.androrm.field.OneToManyField;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class RestaurantModel extends Model {
    protected CharField mTitle;
    protected CharField mDescription;
    protected DoubleField mRating;
    protected IntegerField mVisits;

    protected CharField mPicture;
    protected OneToManyField<RestaurantModel, ReviewModel> mReviewModel;


    // initializes the standard ID field
    // and sets it to autoincrement
    public RestaurantModel() {
        super();
        mTitle = new CharField();
        mDescription = new CharField();
        mPicture = new CharField();
        mRating = new DoubleField();
        mReviewModel = new OneToManyField<RestaurantModel, ReviewModel>(RestaurantModel.class, ReviewModel.class);
        mVisits = new IntegerField();
    }

    public RestaurantModel(String pTitle, String pDescription, double pRating, int pVisits) {
        this();
        mTitle.set(pTitle);
        mDescription.set(pDescription);
        mRating.set(pRating);
        mVisits.set(0);
    }

    public static QuerySet<RestaurantModel> readRestaurants(Context context) {
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

    public int getVisits() {
        return mVisits.get().intValue();
    }

    public void setVisits(int pVisit) {
        mVisits.set(pVisit);
    }

    public void incrementVisit() {
        int i = mVisits.get().intValue();
        mVisits.set(i++);
    }

    public void decrementVisit() {
        int i = mVisits.get().intValue();
        mVisits.set(i--);
    }

    public void addReview(ReviewModel rev) {
        mReviewModel.add(rev);
    }


    public QuerySet<ReviewModel> getReviews(Context context) {
        return mReviewModel.get(context, this);
    }

    /**
     * Retrieves the image from the database that is encoded as a Base64 string
     *
     * @return a bitmap of the stored image
     */
    public File getPictureFile() {
        String _path = mPicture.get();
        if (_path == null) return null;
        return new File(_path);
    }

    /**
     * Retrieves the image from the database that is encoded as a Base64 string
     *
     * @return a bitmap of the stored image
     */
    public Bitmap getPicture() {
        String _path = mPicture.get();
        File f = new File(_path);

        try {
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            Log.v("TAGS", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the database image asa based 64 string
     *
     * @param pPicture the bitmap of the image being stored
     */
    public void setPicture(Bitmap pPicture) {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/images";
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        String name = String.format("%s", new Date().toString());
        File file = new File(dir, name + ".png");
        try {
            FileOutputStream fOut = new FileOutputStream(file);

            pPicture.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
            Log.i("TAG", file.getAbsolutePath());
            this.mPicture.set(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public int getReviewCount(Context context) {
        return mReviewModel.get(context, this).count();
    }
}
