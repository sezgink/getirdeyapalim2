package com.example.sezgink.getirdeyapalim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Intent intent = getIntent();
        final String s = intent.getStringExtra("searchObject");
        Log.i("Export",s);

        RecyclerView recy = findViewById(R.id.recy);
        final List<Record> recordsList = new ArrayList<Record>();
        Log.d("Adapter","List ready");
        Record rec = new Record();
        Log.d("Adapter","Create record");
        //recordsList.add(rec);
        Log.d("Adapter","Added to liset");
        final RecordsAdapter ra = new RecordsAdapter(recordsList);
        Log.d("Adapter","ra is defined");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Log.d("Adapter","ra is defined");
        recy.setLayoutManager(mLayoutManager);
        recy.setItemAnimator(new DefaultItemAnimator());
        Log.d("Adapter","ready to set");
        recy.setAdapter(ra);
        Log.d("Adapter","Fuckin did");

        RequestQueue rq = Volley.newRequestQueue(getBaseContext());
        String url = "https://getir-bitaksi-hackathon.herokuapp.com/searchRecords";
        Log.d("Vole","Before try");
        try {
            Log.d("Vole","After try");
            JSONObject jo = new JSONObject(s);
            JsonObjectRequest jr = new JsonObjectRequest(Request.Method.POST, url, jo, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("Responsee!", "onResponse: " + response.toString());
                    Gson gson = new Gson();
                    Log.d("Ready to parse","Yeah");
                    JSONRes res = gson.fromJson(response.toString(),JSONRes.class);
                    //recordsList.add(res.records.get(0));
                    //recordsList.add(res.records.get(0));
                    for(Record r : res.records) {
                        recordsList.add(r);
                        Log.d("added",r._id._id);
                    }
                    Log.i("Responsee!", "Before notify");
                    ra.notifyDataSetChanged();


                    Log.i("Responsee!", "I parsed " + res.records.get(0)._id.key);


                    // sendPrint(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", "Response Error: " + error.toString());

                }
            });

            rq.add(jr);
        } catch(Exception e) {

        }


    }
}
