package com.josephpconley.pga;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * User: jconley
 * Date: 7/25/2014
 */
public class WidgetHeaderService extends Service {

    private static String tournamentName;
    private static String courseName;
    private static Date lastUpdated;

    @Override
    public IBinder onBind(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

//        RemoteViews views = new RemoteViews(this.getApplicationContext().getPackageName(), R.id.title);


//        String res = HTTP.get("http://www.josephpconley.com/pga");
//        try {
//            JSONObject resJSON = new JSONObject(res);
//            JSONArray players = resJSON.getJSONArray("players");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return null;
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