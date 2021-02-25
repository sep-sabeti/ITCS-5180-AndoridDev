package com.example.inclass05;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SingleRowRecyclerGenreViewAdapter extends RecyclerView.Adapter<SingleRowRecyclerGenreViewAdapter.SingleGenreViewHolder> {
    ArrayList<String>data;

    public SingleRowRecyclerGenreViewAdapter(ArrayList<String>data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SingleGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_card_view_holder,parent,false);
        SingleGenreViewHolder singleGenreViewHolder = new SingleGenreViewHolder(view);
        return singleGenreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SingleGenreViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class SingleGenreViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        SingleRowRecyclerViewAdapter.IAppCategoryClickedInRecyclerView mAppCategoryClickedInRecylerView;
        public SingleGenreViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewSingleRowCardHolderTextView);
        }
    }

}
