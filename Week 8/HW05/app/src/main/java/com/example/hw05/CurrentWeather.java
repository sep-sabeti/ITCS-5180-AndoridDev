package com.example.hw05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CurrentWeather extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Data.City currentCity;
    JSONObject weather = null;
    ICurrentWeatherListener mListner;
    public boolean clickable;
    String icon;
    public CurrentWeather() {
        // Required empty public constructor
    }

    public static CurrentWeather newInstance(Data.City param1) {
        CurrentWeather fragment = new CurrentWeather();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);

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
        TextView city_name;
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        city_name = view.findViewById(R.id.lblCity);
        city_name.setText(currentCity.getCity() + ", " + currentCity.getCountry());
        mListner.backable(false);
        getWeather();
        getActivity().setTitle(R.string.current_weather_label);
        ImageView view1 = view.findViewById(R.id.imageView);
        view1.setImageResource(android.R.color.transparent);

        clickable = false;




        Button forecast = view.findViewById(R.id.btnCheckForecast);

        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickable){
                    mListner.weatherForecastClicked(currentCity);
                }
            }
        });

        return view;
    }


    public void getWeather() {

        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("api.openweathermap.org")
                .addPathSegment("data")
                .addPathSegment("2.5")
                .addPathSegment("weather")
                .addQueryParameter("q",currentCity.getCity())
                .addQueryParameter("units","imperial")
                .addQueryParameter("appid",MainActivity.token)
                .build();

        OkHttpClient client = new OkHttpClient();






        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(MainActivity.TAG, "onResponse: " + url);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if(response.isSuccessful()){
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(MainActivity.TAG, body);

                    try {
                         weather = new JSONObject(body);
                        if(weather != null){
                            JSONObject temp_Related = new JSONObject(weather.getString("main"));
                            JSONArray weatherRelatedArray = weather.getJSONArray("weather");
                            JSONObject weather_related = (JSONObject) weatherRelatedArray.get(0);
                            icon = weather_related.getString("icon");

                            JSONObject wind_related = weather.getJSONObject("wind");
                            JSONObject cloud_related = weather.getJSONObject("clouds");
                            TextView temp = getView().findViewById(R.id.lblDisplayTemperature);
                            TextView temp_max = getView().findViewById(R.id.lblDisplayTempMax);
                            TextView temp_min = getView().findViewById(R.id.lblDisplayTempMin);
                            TextView description = getView().findViewById(R.id.lblDisplayDescription);
                            TextView humidity = getView().findViewById(R.id.lblDisplayHumidity);
                            TextView wind_speed = getView().findViewById(R.id.lblDisplayWindSpeed);
                            TextView wind_degree = getView().findViewById(R.id.lblDisplayWindDegree);
                            TextView cloudiness = getView().findViewById(R.id.lblDisplayCloudiness);

                            HttpUrl.Builder builder1 = new HttpUrl.Builder();
                            HttpUrl url1 = builder1.scheme("https")
                                    .host("openweathermap.org")
                                    .addPathSegment("img")
                                    .addPathSegment("wn")
                                    .addPathSegment(icon+"@2x.png")
                                    .build();


                            Request request1 = new Request.Builder()
                                    .url(url1)
                                    .build();

                            Log.d(MainActivity.TAG, String.valueOf(url1));




                            getActivity().runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {


                                                                try{
                                                                    temp.setText(temp_Related.getDouble("temp") +" "+getContext().getResources().getString(R.string.f));
                                                                }catch (JSONException e){
                                                                    temp.setText("N/A");
                                                                }

                                                                try{
                                                                    temp_max.setText(temp_Related.getDouble("temp_max") +" "+getContext().getResources().getString(R.string.f));

                                                                } catch(JSONException e){
                                                                    temp_max.setText("N/A");

                                                                }

                                                                try{
                                                                    temp_min.setText(temp_Related.getDouble("temp_min")+" F");

                                                                } catch (JSONException e) {
                                                                    temp_min.setText("N/A");
                                                                }

                                                                try{
                                                                    humidity.setText(temp_Related.getDouble("humidity")+" "+getContext().getResources().getString(R.string.percent));

                                                                } catch (JSONException e){
                                                                    humidity.setText("N/A");

                                                                }
                                                                try{
                                                                    description.setText(weather_related.getString("description"));

                                                                }catch (JSONException e){
                                                                    description.setText("N/A");

                                                                }

                                                                try {
                                                                    wind_speed.setText(wind_related.getString("speed")+" "+getContext().getResources().getString(R.string.mph));

                                                                } catch (JSONException e){
                                                                    wind_speed.setText("N/A");

                                                                }

                                                                    try{
                                                                        wind_degree.setText(weather_related.getString("deg")+" "+getContext().getResources().getString(R.string.degree));

                                                                    } catch (JSONException e){
                                                                        wind_degree.setText("N/A");
                                                                    }

                                                                    try{
                                                                        cloudiness.setText(cloud_related.getString("all")+" "+getContext().getResources().getString(R.string.percent));

                                                                    }catch (JSONException e){
                                                                        cloudiness.setText("N/A");
                                                                    }


                                                            }
                                                        }

                            );

                            client.newCall(request1).enqueue(new Callback() {
                                @Override
                                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                }

                                @Override
                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                    if(response.isSuccessful()){
                                        ResponseBody responseBody1 = response.body();
                                        InputStream input =  responseBody1.byteStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(input);

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ImageView imageView = getView().findViewById(R.id.imageView);
                                                imageView.setImageBitmap(bitmap);
                                                mListner.backable(true);
                                                clickable = true;

                                            }
                                        });
                                    }



                                }
                            });


                        }

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
        if(context instanceof ICurrentWeatherListener){
            mListner = (ICurrentWeatherListener)context;

        }
    }

    public interface ICurrentWeatherListener{
        void weatherForecastClicked(Data.City city);
        void backable(boolean status);
    }

}