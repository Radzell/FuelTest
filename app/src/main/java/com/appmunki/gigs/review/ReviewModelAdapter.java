package com.appmunki.gigs.review;

/**
 * Created by radzell on 4/20/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmunki.gigs.R;

import java.util.Random;


/**
 * ADAPTER
 */

public class ReviewModelAdapter extends ArrayAdapter<ReviewModel> {

    private static final String TAG = "ReviewAdapter";
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;

    public ReviewModelAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.fragment_review_list_item, parent, false);
            vh = new ViewHolder();
            vh.mTitleView = (TextView) convertView.findViewById(R.id.titletextView);
            vh.mCommentView = (TextView) convertView.findViewById(R.id.commenttextView);
            vh.mRatingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }




        vh.mTitleView.setText(getItem(position).getTitle());
        vh.mCommentView.setText(getItem(position).getComment());

        vh.mRatingBar.setRating((float) getItem(position).getRating());


        return convertView;
    }



    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

    static class ViewHolder {
        TextView mTitleView;
        RatingBar mRatingBar;
        TextView mCommentView;
    }
}