package com.example.hw05;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardView> {
    ArrayList<WeatherForecastClass> weather;
    WeatherForecast context;
    Activity activity;
    IAdapterListner mListner;

    public CardViewAdapter(ArrayList<WeatherForecastClass> weather , WeatherForecast context , Activity activity){
        this.weather = weather;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card_view,parent,false);
        CardView cardView = new CardView(view);
        return cardView;
    }

    @Override
    public void onBindViewHolder(@NonNull CardView holder, int position) {


        holder.textView.setText(weather.get(position).getTime());
        holder.temp.setText(weather.get(position).getTemp() + " F");
        holder.maxTemp.setText(context.getResources().getString(R.string.max) + " :" + weather.get(position).getMaxTemp());
        holder.minTemp.setText(context.getResources().getString(R.string.min) + " :" + weather.get(position).getMinTemp());
        holder.humidity.setText(context.getResources().getString(R.string.humidity) +" :" + weather.get(position).getHumidity() + " "+context.getResources().getString(R.string.percent) );
        holder.status.setText(weather.get(position).getStatus());

        OkHttpClient client = new OkHttpClient();

        if(weather.get(position).getIcon() != null){
            HttpUrl.Builder builder1 = new HttpUrl.Builder();
            HttpUrl url1 = builder1.scheme("https")
                    .host("openweathermap.org")
                    .addPathSegment("img")
                    .addPathSegment("wn")
                    .addPathSegment(weather.get(position).getIcon()+"@2x.png")
                    .build();

            Request request1 = new Request.Builder()
                    .url(url1)
                    .build();

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
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.imageView.setImageBitmap(bitmap);

                            }
                        });

                        mListner = (IAdapterListner) context;
                        mListner.status(true);

                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.weather.size();
    }

    public static class CardView extends RecyclerView.ViewHolder{

        TextView temp,maxTemp,minTemp,humidity,status, textView;
        ImageView imageView;

        public CardView(@NonNull View itemView) {
            super(itemView);

            temp = itemView.findViewById(R.id.tempDisplay);
            maxTemp = itemView.findViewById(R.id.maxtempDisplay);
            minTemp = itemView.findViewById(R.id.minTempDisplay);
            humidity = itemView.findViewById(R.id.humidityDisplay);
            status = itemView.findViewById(R.id.conditionDisplay);
            textView = itemView.findViewById(R.id.timeView);
            imageView = itemView.findViewById(R.id.iconDisplay);
            imageView.setImageResource(android.R.color.transparent);
        }
    }

    public interface IAdapterListner{
        void status(boolean status);
    }


}
