package com.example.inclass05;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SingleRowRecyclerViewAdapter extends RecyclerView.Adapter<SingleRowRecyclerViewAdapter.SingleRowViewHolder> {
    ArrayList<Object> data;
    IAppCategoryClickedInRecyclerView mAppCategoryClickedInRecylerView;

    public SingleRowRecyclerViewAdapter(ArrayList<Object> data , IAppCategoryClickedInRecyclerView mAppCategoryClickedInRecylerView){
        this.data = data;
        this.mAppCategoryClickedInRecylerView = mAppCategoryClickedInRecylerView;
    }

    @NonNull
    @Override
    public SingleRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_card_view_holder,parent,false);
        SingleRowViewHolder singleRowViewHolder = new SingleRowViewHolder(view , mAppCategoryClickedInRecylerView);
        return singleRowViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleRowViewHolder holder, int position) {

        String text = (String)data.get(position);
        holder.textView.setText(text);
        holder.data = data;

    }

    @Override
    public int getItemCount() {
        Log.d("hey", this.data.size()+"");

        return this.data.size();
    }


    public static class SingleRowViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ArrayList<Object> data;
        IAppCategoryClickedInRecyclerView mAppCategoryClickedInRecylerView;
        public SingleRowViewHolder(@NonNull View itemView , final IAppCategoryClickedInRecyclerView mAppCategoryClickedInRecylerView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewSingleRowCardHolderTextView);
            this.mAppCategoryClickedInRecylerView = mAppCategoryClickedInRecylerView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAppCategoryClickedInRecylerView.selectedAppCategory(textView.getText().toString());
                }
            });

        }
    }

    public interface IAppCategoryClickedInRecyclerView{
        void selectedAppCategory(String appCategory);
    }
}
