package com.appmunki.gigs.dummy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.appmunki.gigs.R;
import com.appmunki.gigs.restaurant.RestaurantModel;
import com.orm.androrm.DatabaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContentModel {
    private static boolean debugging=false;

	public static void createDummyContent(Context context) {
		// Add 3 sample items.
		if (RestaurantModel.readRestaurants(context).isEmpty()&&debugging) {
            DatabaseAdapter adapter = DatabaseAdapter.getInstance(context);
            adapter.beginTransaction();

            List<RestaurantModel> restaurantsList = new ArrayList<RestaurantModel>();
            int [] images = {R.drawable.imga,R.drawable.imgb,R.drawable.imgc,R.drawable.imgd,R.drawable.imge,R.drawable.imgf,R.drawable.imgg};
            Bitmap bitmap;
            Random r = new Random();

            restaurantsList.add(new RestaurantModel("First Restaurant", "Test Description", 4.0,0));
			restaurantsList.add(new RestaurantModel("Second Restaurant", "Test Description", 4.0,0));
			restaurantsList.add(new RestaurantModel("Third Restaurant", "Test Description", 4.0,0));
			restaurantsList.add(new RestaurantModel("Fourth Restaurant", "Test Description", 4.0,0));
            restaurantsList.add(new RestaurantModel("First Restaurant", "Test Description", 4.0,0));
            restaurantsList.add(new RestaurantModel("Second Restaurant", "Test Description", 4.0,0));
            restaurantsList.add(new RestaurantModel("Third Restaurant", "Test Description", 4.0,0));
            restaurantsList.add(new RestaurantModel("Fourth Restaurant", "Test Description", 4.0,0));
            for(int i=0;i<restaurantsList.size();i++){
                RestaurantModel rest = restaurantsList.get(i);
                bitmap = BitmapFactory.decodeResource(context.getResources(), images[r.nextInt(6) ]);
                rest.setPicture(bitmap);
                if(rest.save(context)) {
                    Log.i("TAG", "save");
                }else{
                    Log.i("TAG","not save");
                }
            }

            adapter.commitTransaction();
        }
	}

}
