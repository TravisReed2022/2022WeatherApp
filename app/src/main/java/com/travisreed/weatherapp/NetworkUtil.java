package com.travisreed.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static final String BASE_URL = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/305605";
    private static final String API_KEY = "5PWXR1D7ICrNBGgo8qs3LbMdjRBtoBp0";
    private static final String PARAM_API_KEY = "apikey";
    private static final String METRIC_PARAM = "metric";
    private static final String METRIC_VALUE = "true";
    private static String TAG = "NETWORK_UTIL";

    public static URL buildURL() {
        Uri uri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .appendQueryParameter(METRIC_PARAM, METRIC_VALUE)
                .build();

        URL url = null;

        try {
            url = new URL((uri).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponse(URL url) throws IOException {

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream in = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("//A");
            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                Log.i(TAG, "getResponse: " + scanner.next());
                return null;
            }

        } finally {
            httpURLConnection.disconnect();
        }
    }
}
