package com.lumen.care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

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

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private static JSONArray covidData = new JSONArray();

    class RetrieveFeedTask extends AsyncTask<String, Void, StringBuilder> {

        private Exception exception;
        private HttpURLConnection connection = null;
        BufferedReader reader = null;

        protected StringBuilder doInBackground(String... urls) {
            try {
                URL url = new URL("https://corona-api.com/countries");
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

        protected void onPostExecute(StringBuilder data) {
            // TODO: check this.exception
            // TODO: do something with the feed

            if (exception == null) {
                Log.d("Check", "Works!");
                JSONObject json = null;
                try {
                    json = new JSONObject(data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    assert json != null;
                    covidData = json.getJSONArray("data");
                    ArrayList<Object> list = new ArrayList<>();
                    for (int i = 0; i < covidData.length(); i++) {
                        list.add(covidData.get(i));
                    }
                    SortJsonArray sortJsonArray = new SortJsonArray();
                    sortJsonArray.sortArray(list, "name", true);
                    covidData = new JSONArray();
                    for (Object object : list) {
                        covidData.put(object);
                    }
                    RecyclerView recyclerView = findViewById(R.id.recyclerView2);

                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    recyclerView.setHasFixedSize(false);

                    // use a linear layout manager
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    RecyclerView.Adapter<CustomListAdapter.ListViewHolder> mAdapter = new CustomListAdapter(covidData);
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Couldn't fetch data, try again later.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_api_key));

        setContentView(R.layout.activity_main);

        // Get data
        new RetrieveFeedTask().execute();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments


                    }
                });

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = { "Saint Pierre and Miquelon", "Bosnia and Herzegovina", "Costa Rica", "Georgia", "Sao Tome and Principe", "Montserrat", "Tokelau", "Kazakhstan",
                "Timor-Leste", "Trinidad and Tobago", "Egypt", "Nicaragua", "Czech Republic", "Saint Helena", "Ascension and Tristan da Cunha Turkey", "Norfolk Island",
                "French Guiana", "Fiji", "Dominican Republic", "Jordan", "Curaçao", "British Indian Ocean Territory", "Hungary", "Colombia", "Oman" };



        // specify an adapter (see also next example)
        RecyclerView.Adapter<CustomListAdapter.ListViewHolder> mAdapter = new CustomListAdapter(covidData);
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
