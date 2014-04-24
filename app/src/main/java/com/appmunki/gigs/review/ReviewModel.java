package com.appmunki.gigs.review;

import com.appmunki.gigs.restaurant.RestaurantModel;
import com.orm.androrm.Model;
import com.orm.androrm.field.CharField;
import com.orm.androrm.field.DoubleField;
import com.orm.androrm.field.ForeignKeyField;

public class ReviewModel extends Model {

    protected CharField mTitle;
    protected CharField mComment;
    protected DoubleField mRating;
    protected ForeignKeyField<RestaurantModel> mRestaurantModel;

    // initializes the standard ID field
    // and sets it to autoincrement
    public ReviewModel() {
        super();
        mTitle = new CharField();
        mComment = new CharField();
        mRating = new DoubleField();
        mRestaurantModel = new ForeignKeyField<RestaurantModel>(RestaurantModel.class);

    }

    public ReviewModel(String pTitle, String pComment, double pRating) {
        this();
        mTitle.set(pTitle);
        mComment.set(pComment);
        mRating.set(pRating);

    }

    public String getTitle() {
        return mTitle.get();
    }

    public void setTitle(String pTitle) {
        this.mTitle.set(pTitle);
    }

    public String getComment() {
        return mComment.get();
    }

    public void setComment(String pComment) {
        this.mComment.set(pComment);
    }

    public double getRating() {
        return mRating.get().doubleValue();
    }

    public void setRating(double pRatingS) {
        this.mRating.set(pRatingS);
    }

    public void setReviewedRestaurant(int pID) {
        mRestaurantModel.set(pID);
    }

    public RestaurantModel getReviewedRestaurant() {
        return mRestaurantModel.get();
    }

    public void setReviewedRestaurant(RestaurantModel pRestaurant) {
        mRestaurantModel.set(pRestaurant);
    }
}
