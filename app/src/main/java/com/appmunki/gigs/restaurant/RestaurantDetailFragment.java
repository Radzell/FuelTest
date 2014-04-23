package com.appmunki.gigs.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmunki.gigs.R;
import com.appmunki.gigs.review.ReviewCreateActivity;

/**
 * A fragment representing a single Resturant detail screen. This fragment is
 * either contained in a {@link RestaurantListActivity} in two-pane mode (on
 * tablets) or a {@link RestaurantDetailActivity} on handsets.
 */
public class RestaurantDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_RESTAURANT_ID = "restaurant_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private RestaurantModel mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public RestaurantDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_RESTAURANT_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = RestaurantModel.readResturants(getActivity()).get(getArguments().getInt(
					ARG_RESTAURANT_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_restaurant_detail,
				container, false);

		// Show the content of the restaurant
		if (mItem != null) {
            ((ImageView) rootView.findViewById(R.id.detailImageView)).setImageBitmap(mItem.getPicture());
			((TextView) rootView.findViewById(R.id.visitstextView))
					.setText(""+mItem.getVisits());
            ((TextView) rootView.findViewById(R.id.detailTextView))
                    .setText(""+mItem.getTitle());
            ((RatingBar) rootView.findViewById(R.id.detailsratingBar)).setRating((float) mItem.getRating());
            ((Button) rootView.findViewById(R.id.detailsbutton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), ReviewCreateActivity.class);
                    intent.putExtra(ARG_RESTAURANT_ID,getArguments().getString(
                            ARG_RESTAURANT_ID));
                    startActivity(intent);
                }
            });

        }

		return rootView;
	}
}
