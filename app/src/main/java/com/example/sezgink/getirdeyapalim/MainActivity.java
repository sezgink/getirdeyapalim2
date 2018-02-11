package com.example.sezgink.getirdeyapalim;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    Button minDateB,maxDateB,sendButton;
    TextView minDateText,maxDateText;
    EditText minCountText,maxCountText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minDateText = findViewById(R.id.minDateText);
        maxDateText = findViewById(R.id.maxDateText);
        minDateB = findViewById(R.id.minDateB);
        maxDateB = findViewById(R.id.maxDateB);

        minCountText = findViewById(R.id.minCountText);
        maxCountText = findViewById(R.id.maxCountText);

        Calendar currentDate0 = Calendar.getInstance();
        int year0 = currentDate0.get(Calendar.YEAR);
        int day0 = currentDate0.get(Calendar.DAY_OF_MONTH);
        int month0 = currentDate0.get(Calendar.MONTH);

        minDateText.setText(year0+"-"+(month0+1)+"-"+day0);
        maxDateText.setText(year0+"-"+(month0+1)+"-"+day0);

        Log.i("isWork", "Started");

        minDateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Date","Clickeed");
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                int month = currentDate.get(Calendar.MONTH);
                DatePickerDialog datePicker;
                Log.d("Date","Date Picker created");
                datePicker = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("Date","Lets date");
                      minDateText.setText(year+"-"+(month+1)+"-"+dayOfMonth);

                    }
                },year,month,day);

                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
                datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Iptal", datePicker);
                Log.d("Date","Before Show");
                datePicker.show();
            }
        });
        maxDateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Date","Clickeed");
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);
                int month = currentDate.get(Calendar.MONTH);
                DatePickerDialog datePicker;
                Log.d("Date","Date Picker created");
                datePicker = new DatePickerDialog(MainActivity.this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("Date","Lets date");
                        maxDateText.setText(year+"-"+(month+1)+"-"+dayOfMonth);

                    }
                },year,month,day);
                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
                datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Iptal", datePicker);
                Log.d("Date","Before Show");
                datePicker.show();
            }
        });




        sendButton = findViewById(R.id.sendButton);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("isWork", "OnClick");
                RequestQueue rq = Volley.newRequestQueue(getBaseContext());
                String url = "https://getir-bitaksi-hackathon.herokuapp.com/searchRecords";

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                try{
                    /*
                    jsonObject.put("startDate", "2016-01-26");
                    jsonObject.put("endDate", "2017-02-02");
                    jsonObject.put("minCount", 2700);
                    jsonObject.put("maxCount", 3000);
                    */
                    jsonObject.put("startDate", minDateText.getText().toString());
                    jsonObject.put("endDate", maxDateText.getText().toString());
                    jsonObject.put("minCount", Integer.parseInt(minCountText.getText().toString()));
                    jsonObject.put("maxCount", Integer.parseInt(maxCountText.getText().toString()));

                    jsonArray.put(jsonObject);
                    //Log.i("jsonString", jsonObject.toString());
                    Intent search = new Intent(getBaseContext(),ShowActivity.class);
                    search.putExtra("searchObject",jsonObject.toString());
                    startActivity(search);





                }catch(Exception e){
                    Log.d("JSON Send","Patladı" + e.toString());

                    Context context = getApplicationContext();
                    CharSequence text = "Check your values";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }




            }
        });
    }

    public void sendPrint(String toPrint) {
        Log.i("sendPrint",toPrint);
    }
}


