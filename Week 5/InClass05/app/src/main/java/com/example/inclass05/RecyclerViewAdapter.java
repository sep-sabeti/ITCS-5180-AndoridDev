package com.example.inclass05;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Object> apps;
    private ISelectedAppInRecyclerView iSelectedAppInRecyclerView;

    public RecyclerViewAdapter(ArrayList<Object> apps , ISelectedAppInRecyclerView iSelectedAppInRecyclerView){
        this.apps = apps;
        this.iSelectedAppInRecyclerView = iSelectedAppInRecyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_holder,parent,false);
        ViewHolder viewHolder = new ViewHolder(view , iSelectedAppInRecyclerView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataServices.App app = (DataServices.App)apps.get(position);
        holder.appName.setText(app.name);
        holder.artistName.setText(app.artistName);
        holder.releaseDate.setText(app.releaseDate);
        holder.app = app;


    }

    @Override
    public int getItemCount() {
        return this.apps.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView appName;
        TextView artistName;
        TextView releaseDate;
        DataServices.App app;
        ISelectedAppInRecyclerView iSelectedAppInRecyclerView;
        public ViewHolder(@NonNull View itemView , final ISelectedAppInRecyclerView iSelectedAppInRecyclerView) {
            super(itemView);
            this.iSelectedAppInRecyclerView = (ISelectedAppInRecyclerView) iSelectedAppInRecyclerView;
            appName = itemView.findViewById(R.id.viewHolderName);
             artistName = itemView.findViewById(R.id.viewHolderArtist);
             releaseDate = itemView.findViewById(R.id.viewHolderReleaseDate);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     iSelectedAppInRecyclerView.selectedAppInRecyclerView(app);
                 }
             });
        }
    }

    public interface ISelectedAppInRecyclerView{
        void selectedAppInRecyclerView(DataServices.App app);
    }
}
