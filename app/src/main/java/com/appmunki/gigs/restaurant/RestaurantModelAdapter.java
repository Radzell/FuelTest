package com.appmunki.gigs.restaurant;

/**
 * Created by radzell on 4/20/14.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmunki.gigs.R;

import java.util.ArrayList;
import java.util.Random;

/***
 * ADAPTER
 */

public class RestaurantModelAdapter extends ArrayAdapter<RestaurantModel> {

    private static final String TAG = "SampleAdapter";

    static class ViewHolder {
        ImageView mImageView;
        RatingBar mRatingBar;
        TextView mTextView;
    }

    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public RestaurantModelAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.orange);
        mBackgroundColors.add(R.color.green);
        mBackgroundColors.add(R.color.blue);
        mBackgroundColors.add(R.color.yellow);
        mBackgroundColors.add(R.color.grey);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.fragment_restaurant_grid_item, parent, false);
            vh = new ViewHolder();
            vh.mImageView = (ImageView) convertView.findViewById(R.id.itemimg);
            vh.mTextView = (TextView) convertView.findViewById(R.id.txt_line1);
            vh.mRatingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }




        int count=0;
        int [] images = {R.drawable.imga,R.drawable.imgb,R.drawable.imgc,R.drawable.imgd,R.drawable.imge,R.drawable.imgf,R.drawable.imgg};
        Drawable[] ds = new Drawable[images.length];
        for(int i : images){
            ds[count]=getContext().getResources().getDrawable(i);
            count++;
        }
        Random r = new Random();
        vh.mImageView.setImageDrawable(ds[r.nextInt(6)]);


        vh.mTextView.setText(getItem(position).getTitle() + position);

        vh.mRatingBar.setRating((float) getItem(position).getRating());



        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}