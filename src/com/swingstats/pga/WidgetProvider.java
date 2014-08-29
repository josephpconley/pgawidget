package com.swingstats.pga;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * User: jconley
 * Date: 7/20/2014
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length; i++) {
            Intent svcIntent = new Intent(context, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
            widget.setRemoteAdapter(R.id.golfers, svcIntent);
            widget.setEmptyView(R.id.golfers, R.id.empty_view);

            //manual refresh
            Intent clickIntent = new Intent(context, WidgetProvider.class);
            clickIntent.setAction("REFRESH_LIST");
            PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context,0, clickIntent, 0);
            widget.setOnClickPendingIntent(R.id.refresh, pendingIntentRefresh);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }

        Log.w("JOE", "onUpdate");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equalsIgnoreCase("REFRESH_LIST")){
            Log.w("JOE", "refreshList");
            updateWidget(context);
        }
    }

    private void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.golfers);

//        Log.w("JOE", "onDataSetChanged");
//        new GetScoresTask(AppWidgetManager.getInstance(ctxt), ctxt).execute();
    }
}