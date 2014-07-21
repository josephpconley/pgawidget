package com.swingstats.pga;

import android.os.AsyncTask;

/**
 * User: jconley
 * Date: 7/20/2014
 */
public class GetScoresTask extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String... uri) {
        return HTTP.get(uri[0]);
    }

    protected void onPostExecute(String res){

    }
}
