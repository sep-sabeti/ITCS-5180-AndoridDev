package com.example.inclass08;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    ArrayList<Comment> comments;
    CurrentUser user;
    ICommentHolderListener mListener;


    public CommentAdapter(ArrayList<Comment> comments, CurrentUser user ,ICommentHolderListener mListener ){
        this.comments = comments;
        this.user = user;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_card_holder,parent,false);

        CommentHolder commentHolder = new CommentHolder(view );

        return commentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, final int position) {
         Comment cm;
        try{
            cm = new Comment((Map<String, String>) comments.get(position));
        } catch (java.lang.ClassCastException e){
             cm =  comments.get(position);
        }
        holder.name.setText(cm.getName());
        holder.comment.setText(cm.getDescription());
        holder.date.setText(cm.getDate());

        if(!user.email.equals(cm.getEmail())){
            holder.imageView.setEnabled(false);
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        final Comment finalCm = cm;
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.email.equals(finalCm.email)){
                    mListener.deleteCommentClicked(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.comments.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{

        TextView name,comment,date;
        ImageView imageView;

        public CommentHolder(@NonNull View itemView ) {
            super(itemView);

            name = itemView.findViewById(R.id.commenterName);
            comment = itemView.findViewById(R.id.commenterComment);
            date = itemView.findViewById(R.id.commentDate);
            imageView = itemView.findViewById(R.id.imageViewComment);
        }
    }


    public interface ICommentHolderListener {
        void deleteCommentClicked(int position);

    }

}
