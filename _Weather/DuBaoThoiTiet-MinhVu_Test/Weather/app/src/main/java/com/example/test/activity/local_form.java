package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.R;
import com.example.test.adapter.DBhelper;
import com.example.test.adapter.ShopLocal;
import com.example.test.models.City;
import com.example.test.models.Recycle_City;
import com.example.test.models.Weather_SevenDay;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class local_form extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton btnBack;
    DBhelper dbh;
    String tvCountry, tvCity, tvNhietDo, tvMinMax, tvLineStatus, tvDoAm, tvMay, tvGio, tvHours, tvSunOn, tvSunOff, icon, x, y;
    int i = 0;
    ArrayList<Recycle_City> recycle_cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_form);
        btnBack = (ImageButton) findViewById(R.id.btn_local_back);
        dbh = new DBhelper(local_form.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (local_form.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Anhxa();
        ShowLocal();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void  Anhxa(){
        recycle_cities = new ArrayList<Recycle_City>();
        ShopLocal shopLocal = new ShopLocal(recycle_cities, local_form.this);
        recyclerView.setAdapter(shopLocal);
    }


    private void ShowLocal() {
        final ArrayList<City> cities = dbh.getAllWords();

        for (int j=0; j < cities.size();j++) {
            RequestQueue requestQueue_weather = Volley.newRequestQueue(local_form.this);
            String url_weather = "https://api.openweathermap.org/data/2.5/weather?lat=" + cities.get(j).getLat() + "&lon=" + cities.get(j).getLng() + "&units=metric&appid=92c6161e0d9ddd64a865f69b71a89c31&lang=vi";
            final StringRequest stringRequest_weather = new StringRequest(Request.Method.GET, url_weather, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        final ArrayList<City> cityTry = dbh.getAllWords();
                        tvCity = cityTry.get(recycle_cities.size()).getCity_Name();
                        tvCountry = cityTry.get(recycle_cities.size()).getCountry();
                        //nhận dữ liệu trả về từ api
                        JSONObject jsonObject = new JSONObject(response);
//                        tv_country.setText(formatted_address);
                        String day = jsonObject.getString("dt");
                        long l = Long.valueOf(day);
                        Date date = new Date(l * 1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM HH:mm");
                        String Day = simpleDateFormat.format(date);
                        tvHours = Day;
                        JSONArray jsonArray = jsonObject.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        //get status
                        tvLineStatus = jsonObjectWeather.getString("description");
                        //get image
                        icon = jsonObjectWeather.getString("icon");
//                        Picasso.get().load("http://openweathermap.org/img/wn/" + icon + ".png").into();
                        JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                        String nhietdo = jsonObjectMain.getString("temp");
                        //get temp max, temp min
                        String temp_max = jsonObjectMain.getString("temp_max");
                        String temp_min = jsonObjectMain.getString("temp_min");
                        //Lam tron
                        Double a = Double.valueOf(temp_max);
                        Double b = Double.valueOf(temp_min);
                        String Temp_max = String.valueOf(a.intValue());
                        String Temp_min = String.valueOf(b.intValue());
                        tvMinMax = Temp_min + "°/" + Temp_max + "°";
                        tvDoAm = "Độ ẩm: " + jsonObjectMain.getString("humidity") + "%";
                        Double x = Double.valueOf(nhietdo);
                        String Nhietdo = String.valueOf(x.intValue());
                        tvNhietDo = Nhietdo + "°";
                        JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                        tvGio = "Gió: " + jsonObjectWind.getString("speed") + "m/s";
                        JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                        tvMay = "Mây: " + jsonObjectCloud.getString("all") + "%";
                        JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                        tvSunOn = jsonObjectSys.getString("sunrise");
                        tvSunOff = jsonObjectSys.getString("sunset");
                        l = Long.valueOf(tvSunOn);
                        date = new Date(l * 1000L);
                        simpleDateFormat = new SimpleDateFormat("HH:mm");
                        tvSunOn = "Mặt trời mọc: " + simpleDateFormat.format(date);
                        l = Long.valueOf(tvSunOff);
                        date = new Date(l * 1000L);
                        simpleDateFormat = new SimpleDateFormat("HH:mm");
                        tvSunOff = "Mặt trời lặn: " + simpleDateFormat.format(date);
                        recycle_cities.add(new Recycle_City(tvCountry, tvCity, tvNhietDo, tvMinMax, tvLineStatus
                                , tvDoAm, tvMay, tvGio, tvHours, tvSunOn, tvSunOff, icon));

                        ShopLocal shopLocal = new ShopLocal(recycle_cities, local_form.this);
                        recyclerView.setAdapter(shopLocal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(local_form.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });
        requestQueue_weather.add(stringRequest_weather);

        }
    }

    protected void onStart() {
        super.onStart();
        dbh.openDB();
    }

    protected void onStop() {
        super.onStop();
        dbh.closeDB();
    }
}
