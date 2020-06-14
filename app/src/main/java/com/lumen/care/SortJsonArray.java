package com.lumen.care;

import android.util.Log;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortJsonArray {

    void sortArray(List list, final String keyName, final boolean ascending) {

        Collections.sort(list, new Comparator<JSONObject>() {

            private final String KEY_NAME = keyName;

            @Override
            public int compare(JSONObject o1, JSONObject o2) {

                String val1 = "";
                String val2 = "";

                try {
                    val1 = (String) o1.get(KEY_NAME);
                    val2 = (String) o2.get(KEY_NAME);
                } catch (Exception e) {
                    Log.e("Sort Exception", "Issue when sorting JSONArray", e);
                    e.printStackTrace();
                }

                if (ascending) {
                    return val1.compareToIgnoreCase(val2);
                }
                else {
                    return val2.compareToIgnoreCase(val1);
                }

            }

        });

    }

}
