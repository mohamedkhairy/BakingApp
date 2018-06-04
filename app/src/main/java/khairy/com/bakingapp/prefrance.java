package khairy.com.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

import khairy.com.bakingapp.jsonData.ReviewJson;

public class prefrance {
    public static final String PREFS_NAME = "prefs";

    public static void saveData(Context context, ReviewJson rj) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        prefs.remove("Status_size");
        prefs.putInt("Status_size" , rj.getIngredients().size());

        for (int i =0 ; i < rj.getIngredients().size() ; i++){
            prefs.remove("status_"+i);
            prefs.putString("status_"+i , rj.getIngredients().get(i).getIngredient());
        }

        prefs.apply();
    }

}
