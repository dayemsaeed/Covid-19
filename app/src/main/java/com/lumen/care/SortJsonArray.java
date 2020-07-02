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
                    val1 = String.valueOf(o1.get(KEY_NAME));
                    val2 = String.valueOf(o2.get(KEY_NAME));
                } catch (Exception e) {
                    Log.e("Sort Exception", "Issue when sorting JSONArray", e);
                    e.printStackTrace();
                }

                if (IntCheckHelper.isInteger(val1)) {
                    if (ascending) {
                        return Integer.valueOf(val1).compareTo(Integer.valueOf(val2));
                    }
                    else {
                        return Integer.valueOf(val2).compareTo(Integer.valueOf(val1));
                    }
                }
                else {
                    if (ascending) {
                        return val1.compareToIgnoreCase(val2);
                    }
                    else {
                        return val2.compareToIgnoreCase(val1);
                    }
                }

            }

        });

    }

}
