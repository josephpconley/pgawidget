package com.swingstats.pga;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * User: jconley
 * Date: 7/20/2014
 */
public class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private static ArrayList<JSONObject> items = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public ListViewFactory(Context ctxt, Intent intent) {
        this.context = ctxt;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.w("JOE", "onCreate");
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        Log.w("JOE", "onCount");
        return(items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        JSONObject player = items.get(position);
        RemoteViews row = null;

        try {
            row = new RemoteViews(context.getPackageName(), R.layout.row);
            row.setTextViewText(R.id.position, player.getString("position"));
            row.setTextViewText(R.id.name, player.getString("name"));
            row.setTextViewText(R.id.total, player.getString("total"));
            row.setTextViewText(R.id.thru, player.getString("thru"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        Log.w("JOE", "onDataSetChanged");

        String res = HTTP.get("http://www.swingstats.com/pga");
        try {
            JSONObject resJSON = new JSONObject(res);
            JSONArray players = resJSON.getJSONArray("players");

            items.clear();

            //set header
//                items.add(new JSONObject(resJSON, new String[]{"name", "course"}));

            //set players
            for(int i = 0; i < players.length(); i++){
                items.add(players.getJSONObject(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}