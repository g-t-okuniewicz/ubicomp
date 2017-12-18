package com.example.gokuniewicz.rainfallpal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gokuniewicz on 18.12.2017.
 */

public class WeatherService extends Service {

    static final String TAG = "WeatherService";

    String url="https://api.apixu.com/v1/forecast.json?key=e2ae52c95e5f4b92aff222658171712&q=Dublin";

    double[] rainLevels;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand(...)");
        Log.d(TAG, intent.getStringExtra("lat"));
        Log.d(TAG, intent.getStringExtra("lon"));

        update();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    private void update() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Response : " + response.toString());

                        try {
                            JSONArray hour = response.getJSONObject("forecast")
                                    .getJSONArray("forecastday")
                                    .getJSONObject(0)
                                    .getJSONArray("hour");
                            Log.d(TAG, "hour" + hour.toString());
                            rainLevels = new double[hour.length()];
                            for(int i=0; i<hour.length(); i++) {
                                rainLevels[i] = hour.getJSONObject(i).getDouble("precip_mm");
                                Log.d(TAG, "rainLevels[" + i + "] = " + rainLevels[i]);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "JSON EXCEPTION");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Response error");
                    }
                });

        RequestHandler.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    //private void
}
