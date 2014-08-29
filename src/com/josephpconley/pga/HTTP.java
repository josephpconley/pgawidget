package com.josephpconley.pga;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;

/**
 * User: jconley
 * Date: 7/20/2014
 */
public class HTTP {

    public static HttpClient client = new DefaultHttpClient();

    public static String get(String uri){
        String result = "";
        try {
            HttpResponse response = HTTP.client.execute(new HttpGet(uri));
            result = EntityUtils.toString(response.getEntity());
        } catch(Exception e) {
            e.printStackTrace();
        }

        Log.w("GET result: ", result);
        return result;
    }
}