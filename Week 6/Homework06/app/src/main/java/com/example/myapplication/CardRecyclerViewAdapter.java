package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.AppNameViewHolder> {

    ArrayList<String> appCategories;
    IItemClickedListener iItemClickedListener;


    public CardRecyclerViewAdapter(ArrayList<String> appCategories , IItemClickedListener iItemClickedListener){
        this.appCategories = appCategories;
        this.iItemClickedListener = iItemClickedListener;
    }


    @NonNull
    @Override
    public AppNameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_category_card,parent,false);
        AppNameViewHolder appNameViewHolder = new AppNameViewHolder(view,iItemClickedListener);
        return appNameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppNameViewHolder holder, int position) {
        String appCategory = appCategories.get(position);
        holder.appCategory.setText(appCategory);

    }

    @Override
    public int getItemCount() {
        return this.appCategories.size();
    }

    public static class AppNameViewHolder extends RecyclerView.ViewHolder{

        TextView appCategory;
        IItemClickedListener iItemClickedListener;
        public AppNameViewHolder(@NonNull final View itemView , final IItemClickedListener iItemClickedListener) {
            super(itemView);
            appCategory = itemView.findViewById(R.id.textView);
             this.iItemClickedListener = iItemClickedListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iItemClickedListener.clickedAppCategory(appCategory.getText().toString());
                }
            });
        }
    }


    public interface IItemClickedListener{
         void clickedAppCategory(String selectedAppCategory);
    }
}
