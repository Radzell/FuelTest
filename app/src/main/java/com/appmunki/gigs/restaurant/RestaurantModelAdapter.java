package com.appmunki.gigs.restaurant;

/**
 * Created by radzell on 4/20/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmunki.gigs.R;
import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * ADAPTER
 */

public class RestaurantModelAdapter extends ArrayAdapter<RestaurantModel> {

    private static final String TAG = "SampleAdapter";
    private final LayoutInflater mLayoutInflater;

    public RestaurantModelAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);
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
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        File file =getItem(position).getPictureFile();
        if(file!=null) {
            Picasso.with(getContext()).load(getItem(position).getPictureFile()).
                    placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fit() //
                    .into(vh.mImageView);
        }else{
            Picasso.with(getContext()).load(R.drawable.loading).
                    placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fit() //
                    .into(vh.mImageView);
        }


        vh.mTextView.setText(getItem(position).getTitle());

        vh.mRatingBar.setRating((float) getItem(position).getRating());


        return convertView;
    }



    static class ViewHolder {
        ImageView mImageView;
        RatingBar mRatingBar;
        TextView mTextView;
    }
}