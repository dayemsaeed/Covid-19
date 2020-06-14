package com.lumen.care;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Map;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ListViewHolder> {

    private JSONArray mDataset;

    static class ListViewHolder extends RecyclerView.ViewHolder {

        // each data item that we need to populate
        TextView textView;
        ListViewHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.info_text);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    CustomListAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CustomListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);

        // TextView v = (TextView) linearLayout.findViewById(R.id.info_text);

        return new ListViewHolder(linearLayout);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            holder.textView.setText(mDataset.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }

}
