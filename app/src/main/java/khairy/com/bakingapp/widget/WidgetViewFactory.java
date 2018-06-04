package khairy.com.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import khairy.com.bakingapp.R;
import khairy.com.bakingapp.jsonData.ReviewJson;

import static khairy.com.bakingapp.prefrance.PREFS_NAME;

public class WidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {



    private Context ctxt;
    private int size;
    private SharedPreferences prefs;


    public WidgetViewFactory(Context ctxt) {
        this.ctxt=ctxt;
    }

    @Override
    public void onCreate() {
        prefs = ctxt.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        size = prefs.getInt("Status_size" , 0);

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(ctxt.getPackageName() , R.layout.widget_text);
        String savedText = prefs.getString("status_"+i , "");
        remoteViews.setTextViewText(R.id.widgettxt , savedText);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
