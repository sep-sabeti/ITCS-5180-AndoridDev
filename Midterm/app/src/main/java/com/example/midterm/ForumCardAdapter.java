package com.example.midterm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ForumCardAdapter extends RecyclerView.Adapter<ForumCardAdapter.ForumSingleViewHolder> {

    ArrayList<DataServices.Comment> comments;
    public DataServices.AuthResponse account;
    ICommentListener mListenr;

    public ForumCardAdapter(ArrayList<DataServices.Comment> comments ,  DataServices.AuthResponse account , ICommentListener mListenr){
        this.comments = comments;
        this.account = account;
        this.mListenr = mListenr;
    }

    @NonNull
    @Override
    public ForumSingleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comment,parent,false);
        ForumSingleViewHolder holder = new ForumSingleViewHolder(view , mListenr);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForumSingleViewHolder holder, final int position) {
        holder.name.setText(comments.get(position).createdBy.getName());
        holder.description.setText(comments.get(position).text);
        holder.date.setText(comments.get(position).createdAt.toString());

        if(comments.get(position).createdBy.getName() != account.getAccount().getName()){
            holder.delete.setImageResource(android.R.color.transparent);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(comments.get(position).createdBy.getName() == account.getAccount().getName()){
                    mListenr.clickedComment(comments.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    public static class ForumSingleViewHolder extends RecyclerView.ViewHolder{
        TextView name, description , date;
        ImageView delete;
        ICommentListener mListener;
        public ForumSingleViewHolder(@NonNull View itemView , ICommentListener mListener) {
            super(itemView);

            name = itemView.findViewById(R.id.commentorName);
            description = itemView.findViewById(R.id.commenterComment);
            date = itemView.findViewById(R.id.commentDate);
            delete = itemView.findViewById(R.id.deleteCommentIcon);
            this.mListener = mListener;

        }
    }

    public interface ICommentListener{
        void clickedComment(DataServices.Comment comment);
    }

}
