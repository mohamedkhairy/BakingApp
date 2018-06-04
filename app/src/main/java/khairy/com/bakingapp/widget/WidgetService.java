package khairy.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViewsService;

import khairy.com.bakingapp.jsonData.ReviewJson;
import khairy.com.bakingapp.prefrance;

public class WidgetService extends RemoteViewsService {

    public static void updateWidget(Context context) {


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidget.class));
        IngredientsWidget.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetViewFactory(this.getApplicationContext());
    }


}
