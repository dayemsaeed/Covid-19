package com.lumen.care;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.NumberFormat;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ListViewHolder> {

    private JSONArray mDataset;
    private String murl;

    static class ListViewHolder extends RecyclerView.ViewHolder {

        // each data item that we need to populate
        TextView textView;
        TextView textView2;
        ImageView imageView;
        ListViewHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.name);
            textView2 = v.findViewById(R.id.cases);
            imageView = v.findViewById(R.id.image);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    CustomListAdapter(JSONArray myDataset, String url) {
        mDataset = myDataset;
        murl = url;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CustomListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);

        return new ListViewHolder(linearLayout);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String textViewString = "";
        String textView2String = "";
        String url = "";

        try {
            if (murl.contains("api.covid19api.com")) {
                textViewString = mDataset.getJSONObject(position).getString("Country");
                textView2String = NumberFormat.getInstance().format(mDataset.getJSONObject(position).getInt("TotalConfirmed"));
            }
            else if (murl.contains("newsapi.org")) {
                textViewString = mDataset.getJSONObject(position).getString("title");
                textView2String = mDataset.getJSONObject(position).getString("description");
                url = mDataset.getJSONObject(position).getString("url");
                String finalUrl = url;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(finalUrl));
                        v.getContext().startActivity(i);
                    }
                });
            }
            holder.textView.setText(textViewString);
            holder.textView2.setText(textView2String);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return  mDataset == null ? 0 : mDataset.length();
    }

}
