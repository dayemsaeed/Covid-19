package com.lumen.care;

import android.os.AsyncTask;
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

public class NewsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    JSONArray news;
    String newsUrl;

    public NewsFragment(JSONArray data, String url) {
        news = data;
        newsUrl = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_page, container, false);
        recyclerView = view.findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        RecyclerView.Adapter<CustomListAdapter.ListViewHolder> mAdapter = new CustomListAdapter(news, newsUrl);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

}
