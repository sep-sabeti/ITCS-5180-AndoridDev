package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardViewHolderAdapter extends RecyclerView.Adapter<CardViewHolderAdapter.CardViewHolder> {
    ArrayList<DataServices.App> apps;
   ICardViewAppListner mTopPaidAppListener;


    public CardViewHolderAdapter( ArrayList<DataServices.App> apps , ICardViewAppListner mTopPaidAppListener){
        this.apps = apps;
        this.mTopPaidAppListener = mTopPaidAppListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        CardViewHolder cardViewHolder = new CardViewHolder(view , mTopPaidAppListener);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        holder.appName.setText(apps.get(position).name);
        holder.appArtist.setText(apps.get(position).artistName);
        holder.appReleaseDate.setText(apps.get(position).releaseDate);
        holder.app=apps.get(position);
    }

    @Override
    public int getItemCount() {
        return this.apps.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView appName,appArtist,appReleaseDate;
        ICardViewAppListner mTopPaidAppListener;
        DataServices.App app;

        public CardViewHolder(@NonNull View itemView , final ICardViewAppListner mTopPaidAppListener) {
            super(itemView);
            this.mTopPaidAppListener = mTopPaidAppListener;
            appName = itemView.findViewById(R.id.appName);
            appArtist = itemView.findViewById(R.id.artistName);
            appReleaseDate = itemView.findViewById(R.id.appReleaseDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTopPaidAppListener.clickedApp(app);
                    mTopPaidAppListener.goToAppStatus(true);
                }
            });
        }
    }


    public interface ICardViewAppListner {
        void clickedApp(DataServices.App app);
        void goToAppStatus(boolean status);
    }
}
