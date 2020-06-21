package com.lumen.care;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StatsLoader extends AsyncTaskLoader<StringBuilder> {

    Exception exception;
    String urlString = "";
    static JSONArray covidData = new JSONArray();
    ViewPager2 viewPager2;

    public StatsLoader(@NonNull Context context, String url, ViewPager2 v) {
        super(context);
        urlString = url;
        viewPager2 = v;
    }

    @Nullable
    @Override
    public StringBuilder loadInBackground() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    @Override
    public void deliverResult(@Nullable StringBuilder data) {
        super.deliverResult(data);
        if (exception == null) {
            Log.d("Check", "Works!");
            JSONObject json = null;
            try {
                assert data != null;
                json = new JSONObject(data.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                assert json != null;
                covidData = json.getJSONArray("Countries");
                ArrayList<Object> list = new ArrayList<>();
                for (int i = 0; i < covidData.length(); i++) {
                    list.add(covidData.get(i));
                }
                SortJsonArray sortJsonArray = new SortJsonArray();
                sortJsonArray.sortArray(list, "TotalConfirmed", false);
                covidData = new JSONArray();
                for (Object object : list) {
                    covidData.put(object);
                }
                ViewPager2 pager = findViewById(R.id.view_pager);

                /*View view = getLayoutInflater().inflate(R.layout.view_page, null);
                pageAdapter.addFragment(new StatsFragment(), getString(R.string.stats_tab));*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }



}
