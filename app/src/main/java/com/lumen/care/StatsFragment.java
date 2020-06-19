package com.lumen.care;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

public class StatsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    JSONArray covidData;

    public StatsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_page, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView2);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(false);
        RecyclerView.Adapter<CustomListAdapter.ListViewHolder> mAdapter = new CustomListAdapter(covidData);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
}
