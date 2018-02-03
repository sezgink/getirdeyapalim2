package com.example.sezgink.getirdeyapalim;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("isWork", "Started");

        Button sendButton = findViewById(R.id.sendButton);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("isWork", "OnClick");
                RequestQueue rq = Volley.newRequestQueue(getBaseContext());
                String url = "https://getir-bitaksi-hackathon.herokuapp.com/searchRecords";

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                try{
                    jsonObject.put("startDate", "2016-01-26");
                    jsonObject.put("endDate", "2017-02-02");
                    jsonObject.put("minCount", 2700);
                    jsonObject.put("maxCount", 3000);
                    jsonArray.put(jsonObject);
                    //Log.i("jsonString", jsonObject.toString());
                    Intent search = new Intent(getBaseContext(),ShowActivity.class);
                    search.putExtra("searchObject",jsonObject.toString());
                    startActivity(search);





                }catch(Exception e){

                }




            }
        });
    }

    public void sendPrint(String toPrint) {
        Log.i("sendPrint",toPrint);
    }
}


