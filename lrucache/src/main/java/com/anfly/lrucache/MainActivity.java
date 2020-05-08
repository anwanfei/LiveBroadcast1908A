package com.anfly.lrucache;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, false);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.get(2);

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Log.e("TAG", entry.getKey() + ":" + entry.getValue());
        }

    }
}
