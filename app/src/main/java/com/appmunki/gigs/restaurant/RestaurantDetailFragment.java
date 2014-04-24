package com.appmunki.gigs.restaurant;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.appmunki.gigs.R;
import com.appmunki.gigs.review.ReviewModelAdapter;
import com.squareup.picasso.Picasso;

/**
 * A fragment representing a single Restaurant detail screen. This fragment is
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
        Log.i("TAG", "OnCreate: " + this.getClass().getSimpleName());

        if (getArguments().containsKey(ARG_RESTAURANT_ID)) {

            mItem = RestaurantModel.readRestaurants(getActivity()).get(getArguments().getInt(
                    ARG_RESTAURANT_ID));
        }
    }

    @Override
    public void onResume() {
        Log.i("TAG", "OnResume: " + this.getClass().getSimpleName());
        loadData();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restaurant_detail,
                container, false);
    }

    private void loadData() {
        // Show the content of the restaurant
        if (mItem != null) {
            Picasso.with(getActivity()).load(mItem.getPictureFile()).
                    placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fit() //
                    .into(((ImageView) getView().findViewById(R.id.detailImageView)));
            ((EditText) getView().findViewById(R.id.visitstextView))
                    .setText("" + mItem.getVisits());

            ((TextView) getView().findViewById(R.id.detailTextView))
                    .setText("" + mItem.getTitle());
            ((RatingBar) getView().findViewById(R.id.detailsratingBar)).setRating((float) mItem.getRating());

            ReviewModelAdapter mReviewAdapter = new ReviewModelAdapter(getActivity(), R.id.txt_line1);
            try {
                mReviewAdapter.addAll(mItem.getReviews(getActivity()).all().toList());
            }catch(Exception e){

            }
            ((ListView) getView().findViewById(R.id.detailsreviewlistview)).setAdapter(mReviewAdapter);



        }
    }

    private void updateVisits() {
        EditText visitsEditText = ((EditText) getView().findViewById(R.id.visitstextView));

        visitsEditText.setError(null);

        String visits = visitsEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;
        int visitnum = 0;
        // Check for a valid password.
        if (TextUtils.isEmpty(visits)) {
            visitsEditText.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = visitsEditText;
        } else {
            visitnum = Integer.valueOf(visits);
            if (visitnum < 0) {
                visitsEditText.setError(getString(R.string.error_invalid_real_number));
                cancel = true;
                focusView = visitsEditText;
            }
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mItem.setVisits(visitnum);
            mItem.save(getActivity());
        }
    }
}
