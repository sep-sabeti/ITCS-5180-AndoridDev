package com.example.hw05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class WeatherForecast extends Fragment implements CardViewAdapter.IAdapterListner {


    private static final String ARG_PARAM1 = "param1";
    public JSONObject API_Return;
    ArrayList<WeatherForecastClass> weather = new ArrayList<>();
    IWeatherForecastListener mListener;
    // TODO: Rename and change types of parameters
    private Data.City currentCity;
    WeatherForecast weatherForecastContext;
    public WeatherForecast() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WeatherForecast newInstance(Data.City city) {
        WeatherForecast fragment = new WeatherForecast();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCity = (Data.City) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        getActivity().setTitle(R.string.weather_forecast);
        getForecast();
        mListener.backableWeatherForecast(false);
         weatherForecastContext = this;
        return view;
    }


    public void getForecast(){

        OkHttpClient client = new OkHttpClient();


        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("forecast")
                .addQueryParameter("q",currentCity.getCity())
                .addQueryParameter("units","imperial")
                .addQueryParameter("appid",MainActivity.token)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(MainActivity.TAG, "onResponse: " + "fail");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.isSuccessful()){
                    Log.d(MainActivity.TAG, "onResponse: " + url);
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(MainActivity.TAG, "onResponse: " + body);
                    try {
                        API_Return = new JSONObject(body);
                        JSONArray forecastLists = API_Return.getJSONArray("list");
                        Calendar now = Calendar.getInstance();

                        for(int i = 0 ;i<forecastLists.length();i++){

                            JSONObject object = forecastLists.getJSONObject(i);
                            JSONObject object1 = object.getJSONObject("main");
                            JSONArray array = object.getJSONArray("weather");
                            JSONObject object2 = array.getJSONObject(0);

                            String temp , minTemp,maxTemp,humidity,condition,icon = null;
                            try{
                                temp = object1.getDouble("temp")+"";
                            } catch (JSONException e){
                                 temp = "N/A";
                            }
                            try{
                                 maxTemp = object1.getDouble("temp_max")+"";

                            } catch (JSONException e){
                                 maxTemp = "N/A";
                            }
                            try{
                                 minTemp = object1.getDouble("temp_min")+"";

                            } catch (JSONException e){
                                 minTemp = "N/A";

                            }
                            try{
                                 humidity = object1.getDouble("humidity")+"";

                            } catch (JSONException e){
                                 humidity = "N/A";
                            }
                            try{
                                 condition = object2.getString("description");
                            } catch (JSONException e){
                                 condition = "N/A";
                            }
                            try {
                                icon = object2.getString("icon");

                            } catch (JSONException e){

                            }

                            now.add(Calendar.HOUR,3);
                            String time = now.getTime().toString();
                            WeatherForecastClass weatherToBeAdded = new WeatherForecastClass(temp,minTemp,maxTemp,humidity,condition,time,icon);
                            weather.add(weatherToBeAdded);
                        }


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewForecast);
                                CardViewAdapter adapter = new CardViewAdapter(weather,weatherForecastContext,getActivity());
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IWeatherForecastListener){
            mListener = (IWeatherForecastListener)context;
        }
    }

    @Override
    public void status(boolean status) {
        if(status){
            mListener.backableWeatherForecast(true);
        }
    }

    public interface IWeatherForecastListener{
        void backableWeatherForecast(boolean status);
    }
}