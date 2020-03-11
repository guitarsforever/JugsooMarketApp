package com.example.jugsoomarket.Utils;

import android.content.Context;

import androidx.core.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class dbProcessor {

    public Pair<Map <String, Object>, Map<String,String>> processDB (Context C) {
        try {
            JSONArray jsonArray = new JSONArray(readJSONFromAsset(C));
            return JsonArrayToMap(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String readJSONFromAsset(Context C) {
        String json = null;
        try {
            InputStream is = C.getAssets().open("db.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public Pair<Map <String, Object>, Map<String,String>> JsonArrayToMap (JSONArray jsonArray) throws JSONException {
        Map<String, Object> objectMap = new HashMap<String, Object>();
        Map<String, String> qrMap = new HashMap<String, String>();
        for (int i=0; i< jsonArray.length(); i++) {
            String id = jsonArray.getJSONObject(i).getString("id");
            String qrCode = jsonArray.getJSONObject(i).getString("qrUrl");
            Object object = jsonArray.getJSONObject(i);
            objectMap.put(id, object);
            qrMap.put(qrCode, id);
        }
        return new  Pair<Map <String, Object>, Map<String,String>> (objectMap, qrMap);
    }
}
