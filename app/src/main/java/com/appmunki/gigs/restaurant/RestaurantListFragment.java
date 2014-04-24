package com.appmunki.gigs.restaurant;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.appmunki.gigs.R;
import com.orm.androrm.Filter;
import com.orm.androrm.QuerySet;

import java.util.List;

/**
 * A list fragment representing a list of Restaurants. This fragment also
 * supports tablet devices by allowing list items to be given an 'activated'
 * state upon selection. This helps indicate which item is currently being
 * viewed in a {@link RestaurantDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class RestaurantListFragment extends Fragment implements AdapterView.OnItemClickListener {
    /**
     * A set of filters fo the grid
     */
    Filter[] mFilters = {new Filter().is("mId", ""), new Filter().is("mVisits", ">", 5), new Filter().is("mRating", ">", 3)};


    /**
     * The tag of all the logging for this fragment. Uses the based name of the class.
     */
    String TAG = this.getClass().getSimpleName();
    /**
     * Etsy implementation of a staggeredgrid
     */
    GridView mGridView;
    /**
     * Adapter for manipulation of the staggered grid
     */
    RestaurantModelAdapter mAdapter;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = null;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i("TAG", "OnCreate: " + this.getClass().getSimpleName());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_grid,
                container, false);
        Log.i("TAG", "OnCreateView: " + this.getClass().getSimpleName());

        return rootView;
    }

    @Override
    public void onResume() {
        Log.i("TAG", "OnResume: " + this.getClass().getSimpleName());
        filterRestaurants(0);
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView = (GridView) getView().findViewById(R.id.grid_view);

        //Retrieves the data for the grid
        List<RestaurantModel> data = RestaurantModel.readRestaurants(getActivity()).all().toList();
        Log.i("TAG","count: "+data.size());
        //Initialization of the restaurant adapter
        mAdapter = new RestaurantModelAdapter(getActivity(), R.id.txt_line1);
        mAdapter.addAll(data);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        mCallbacks.onItemSelected(((RestaurantModel) mGridView.getItemAtPosition(position)).getId());
    }


    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {

    }

    public void filterRestaurants(int position) {

        Filter filter = mFilters[position];
        Log.i(TAG, "filter: " + filter.toString());

        QuerySet<RestaurantModel> data = RestaurantModel.readRestaurants(getActivity()).all();
        if (position != 0)
            data.filter(filter);
        Log.i(TAG, "query: " + data.toString());
        Log.i(TAG, "query: " + data.count());


        mAdapter.clear();
        mAdapter.addAll(data.toList());
        mAdapter.notifyDataSetChanged();
    }


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(int position);
    }
}
