package com.lumen.care;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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

import javax.net.ssl.HttpsURLConnection;

public class NewsLoader extends AsyncTask<String, Void, JSONArray> {

    Exception exception;
    String urlString = "";
    static JSONArray news = new JSONArray();
    ViewPageAdapter pageAdapter;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    public NewsLoader(String url, ViewPageAdapter adapter, ViewPager2 pager, TabLayout tabs) {
        super();
        urlString = url;
        pageAdapter = adapter;
        viewPager2 = pager;
        tabLayout = tabs;
    }

    @Nullable
    @Override
    public JSONArray doInBackground(String ... urls) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.addRequestProperty("Authorization", "Bearer baef544b7dbe4ff8b15bb502d1fd5e1a");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder buffer = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            JSONObject json = new JSONObject(buffer.toString());
            news = json.getJSONArray("articles");
            return news;

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

    protected void onPostExecute(JSONArray newsData) {

        if (this.exception == null) {
            Log.d("Check", "Works!");
            pageAdapter.addFragment(new NewsFragment(newsData, urlString), "News");
            viewPager2.setAdapter(pageAdapter);
            new TabLayoutMediator(tabLayout, viewPager2,
                    (tab, position) -> {
                        switch (position) {
                            case 0:
                                tab.setText("Stats");
                                break;
                            case 1:
                                tab.setText("News");
                                break;
                            case 2:
                                tab.setText("Symptoms");
                                break;
                            case 3:
                                tab.setText("Safety");
                                break;
                            default:
                                break;
                        }
                    }).attach();
        }

    }
}
