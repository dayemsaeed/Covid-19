package com.lumen.care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox Access token
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_api_key));

        setContentView(R.layout.activity_main);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap -> mapboxMap.setStyle(Style.DARK, style -> {

            // Map is set up and the style has loaded. Now you can add data or make other map adjustments


        }));

        ViewPager2 pager = findViewById(R.id.view_pager);

        ViewPageAdapter pageAdapter = new ViewPageAdapter(getSupportFragmentManager(), getLifecycle());
        pager.setAdapter(pageAdapter);

        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        TabLayout tabLayout = findViewById(R.id.tabLayout2);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        // Get data
        new StatsLoader("https://api.covid19api.com/summary", pageAdapter, pager, tabLayout).execute();
        new NewsLoader("https://newsapi.org/v2/top-headlines?q=coronavirus&language=en&sortBy=popularity", pageAdapter, pager, tabLayout).execute();

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
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
