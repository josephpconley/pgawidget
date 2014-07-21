package com.swingstats.pga;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    private static ArrayList<String> items = new ArrayList();
    private Context ctxt = null;
    private int appWidgetId;

    public ListViewFactory(Context ctxt, Intent intent) {
        this.ctxt = ctxt;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.w("JOE", "onCreate");
//        for(int i = 0; i < 100; i++){
//            items.add("Item " + i);
//        }
        new GetScoresTask().execute("http://www.swingstats.com/majors/leaderboardPicks?id=15&random=1");
    }

    class GetScoresTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... uri) {
            return HTTP.get(uri[0]);
        }

        protected void onPostExecute(String res){
            try {
                JSONObject resJSON = new JSONObject(res);
                JSONArray made = resJSON.getJSONArray("made");
                for(int i = 0; i < made.length(); i++){
                    items.add(made.getJSONObject(i).getString("proName"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return(items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(), R.layout.row);

        row.setTextViewText(android.R.id.text1, items.get(position));

        Intent i=new Intent();
        Bundle extras=new Bundle();

        extras.putString(WidgetProvider.EXTRA_WORD, items.get(position));
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
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
        // no-op
    }
}