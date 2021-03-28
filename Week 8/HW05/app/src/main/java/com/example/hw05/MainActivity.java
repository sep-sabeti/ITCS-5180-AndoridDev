package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CitiesList.ICitiesListener, CurrentWeather.ICurrentWeatherListener, WeatherForecast.IWeatherForecastListener {
    public Data.City currentCity = null;
    public static String token = "c28d4ca45f9e0369862d554d60bfadeb";
    public static String TAG = "App";

    public boolean backable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.cities_label);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new CitiesList())
                .commit();
    }

    @Override
    public void onBackPressed() {

        if(backable){
            super.onBackPressed();
        }
    }

    @Override
    public void clickedCity(Data.City city) {
        currentCity = city;

        setTitle(R.string.current_weather_label);
        if(currentCity != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CurrentWeather.newInstance(currentCity))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void backableCitiesList(boolean status) {
        backable = status;
    }

    @Override
    public void weatherForecastClicked(Data.City city) {
        currentCity = city;
        setTitle(R.string.weather_forecast);
        if(currentCity != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, WeatherForecast.newInstance(currentCity))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void backable(boolean status) {
        backable = status;
    }

    @Override
    public void backableWeatherForecast(boolean status) {
        backable = status;
    }
}