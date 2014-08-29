package com.josephpconley.pga;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import org.json.JSONObject;

/**
 * User: jconley
 * Date: 7/20/2014
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0; i<appWidgetIds.length; i++) {
            Intent players = new Intent(context, WidgetPlayersService.class);

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
            widget.setRemoteAdapter(R.id.golfers, players);
            widget.setEmptyView(R.id.golfers, R.id.empty_view);

            //metadata
//            new GetTournamentMeta(widget).execute();

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
    }

//    class GetTournamentMeta extends AsyncTask<Void, Void, String> {
//
//        private RemoteViews rv;
//
//        public GetTournamentMeta(RemoteViews rv){
//            this.rv = rv;
//        }
//
//        @Override
//        protected String doInBackground(Void... uri) {
//            return HTTP.get("http://www.swingstats.com/pga");
//        }
//
//        protected void onPostExecute(String res){
//            try {
//                JSONObject resJSON = new JSONObject(res);
//                String name = resJSON.getString("name");
//                String course = resJSON.getString("course");
//
//                rv.setTextViewText(R.id.title, name + "\r" + course);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}