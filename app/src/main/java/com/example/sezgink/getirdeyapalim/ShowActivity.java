package com.example.sezgink.getirdeyapalim;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    public int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String s = intent.getStringExtra("searchObject");
        Log.i("Export",s);

        RecyclerView recy = findViewById(R.id.recy);
        final List<Record> recordsList = new ArrayList<Record>();
        final List<Record> pageList = new ArrayList<Record>();
        Log.d("Adapter","List ready");

        Log.d("Adapter","Create record");
        //recordsList.add(rec);
        Log.d("Adapter","Added to liset");
        final RecordsAdapter ra = new RecordsAdapter(pageList);
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
                    Log.d("Records","Records list.size:"+Integer.toString(recordsList.size()));
                    if(recordsList.size()>(page+1)*10) {
                        for(int i=page*10;i<(page+1)*10;i++) {
                            pageList.add(recordsList.get(i));
                        }
                    } else {
                        for(int i=page*10;i<recordsList.size();i++) {
                            pageList.add(recordsList.get(i));
                        }
                    }

                    Log.i("Responsee!", "Before notify");

                    ra.notifyDataSetChanged();

                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

                    if(recordsList.size()<1) {
                        Context context = getApplicationContext();
                        CharSequence text = "No records";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        TextView t1 = findViewById(R.id.errorText1);
                        TextView t2 = findViewById(R.id.errorText2);

                        t1.setVisibility(View.VISIBLE);
                        t2.setVisibility(View.VISIBLE);

                    }



                    // sendPrint(response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", "Response Error: " + error.toString());
                    TextView t1 = findViewById(R.id.errorText1);

                    findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                    t1.setText("Error in response");
                    t1.setVisibility(View.VISIBLE);

                }
            });

            rq.add(jr);
        } catch(Exception e) {
            TextView t1 = findViewById(R.id.errorText1);

            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            t1.setText("Error");
            t1.setVisibility(View.VISIBLE);

        }
        Button bUp = findViewById(R.id.buttonUp);
        Button bDown =findViewById(R.id.buttonDown);
        final TextView pageText = findViewById(R.id.pageText);

        bUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page<recordsList.size()/10)
                    page += 1;
                pageList.clear();

                if(recordsList.size()>(page+1)*10) {
                    for(int i=page*10;i<(page+1)*10;i++) {
                        pageList.add(recordsList.get(i));
                    }
                } else {
                    for(int i=page*10;i<recordsList.size();i++) {
                        pageList.add(recordsList.get(i));
                    }
                }
                pageText.setText(Integer.toString(page+1));
                ra.notifyDataSetChanged();
            }
        });
        bDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>0) {
                    page -= 1;
                }
                pageList.clear();
                if(recordsList.size()>(page+1)*10) {
                    for(int i=page*10;i<(page+1)*10;i++) {
                        pageList.add(recordsList.get(i));
                    }
                } else {
                    for(int i=page*10;i<recordsList.size();i++) {
                        pageList.add(recordsList.get(i));
                    }
                }
                pageText.setText(Integer.toString(page+1));
                ra.notifyDataSetChanged();
            }
        });





    }
    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
        finish();
        return true;
    }
}
