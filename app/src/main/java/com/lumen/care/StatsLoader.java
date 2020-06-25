package com.lumen.care;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

public class StatsLoader extends AsyncTask<String, Void, JSONArray> {

    Exception exception;
    String urlString = "";
    static JSONArray covidData = new JSONArray();
    ViewPageAdapter pageAdapter;
    ViewPager2 viewPager2;

    public StatsLoader(String url, ViewPageAdapter adapter, ViewPager2 pager) {
        super();
        urlString = url;
        pageAdapter = adapter;
        viewPager2 = pager;
    }

    @Nullable
    @Override
    public JSONArray doInBackground(String ... urls) {
        HttpsURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            JSONObject json = new JSONObject(buffer.toString());
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
            return covidData;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
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

    protected void onPostExecute(JSONArray coviddata) {

        if (this.exception == null) {
            Log.d("Check", "Works!");
            pageAdapter.addFragment(new StatsFragment(coviddata), "Stats");
            viewPager2.setAdapter(pageAdapter);
        }

    }


}