package com.example.midterm;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumHolder> {
    public ArrayList<DataServices.Forum> forums;
    public static DataServices.AuthResponse thisAccount;
    public static String like ;
    public IRecyclerViewListener mListener;
    public boolean likeStatus;
    public boolean createdByLoggedIn;


    public ForumAdapter( ArrayList<DataServices.Forum> forums , DataServices.AuthResponse account , IRecyclerViewListener mListener){
        this.forums = forums;
        this.thisAccount = account;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ForumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        ForumHolder holder = new ForumHolder(view , mListener);
        like = view.getResources().getString(R.string.like);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ForumHolder holder, final int position) {


    holder.forum = forums.get(position);

        holder.forumName.setText(forums.get(position).getTitle());
        holder.forumOwner.setText(forums.get(position).getCreatedBy().getName());
        if(forums.get(position).getDescription().length() > 200){
            holder.forumDescription.setText(forums.get(position).getDescription().substring(0,200));
        } else {
            holder.forumDescription.setText(forums.get(position).getDescription());
        }

        holder.likes.setText(forums.get(position).getLikedBy().size() + " " + like);
        holder.Date.setText(forums.get(position).getCreatedAt().toString());
        Log.d("hey", "onBindViewHolder: " +forums.get(position).getLikedBy() );

        for (DataServices.Account account:forums.get(position).getLikedBy()
             ) {
            if(account.getName() == thisAccount.getAccount().getName()){
                holder.likeIcon.setImageResource(R.drawable.like_favorite);
                likeStatus = true;
                break;
            } else {
                likeStatus = false;
            }
        }

        if(forums.get(position).getCreatedBy().getName() != thisAccount.getAccount().getName()){
            holder.deleteIcon.setImageResource(android.R.color.transparent);
        } else {
        }

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mListener.likeClicked(likeStatus,forums.get(position));
            }
        });

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(forums.get(position).getCreatedBy().getName() == thisAccount.getAccount().getName()){
                    mListener.deleteIconClicked(forums.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.forums.size();
    }

    public static class ForumHolder extends RecyclerView.ViewHolder{

        TextView forumName;
        TextView forumOwner;
        TextView forumDescription;
        TextView likes;
        TextView Date;
        ImageView likeIcon;
        ImageView deleteIcon;
        IRecyclerViewListener mListener;
        DataServices.Forum forum;

        public ForumHolder(@NonNull View itemView , final IRecyclerViewListener mListener) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            mListener.forumClicked(forum);
                }
            });

            forumName = itemView.findViewById(R.id.forumName);
            forumOwner = itemView.findViewById(R.id.forumOwner);
            forumDescription = itemView.findViewById(R.id.forumDescription);
            likes = itemView.findViewById(R.id.numberofLikes);
            Date = itemView.findViewById(R.id.date);
            likeIcon = itemView.findViewById(R.id.likeIcon);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
            this.mListener = mListener;

        }
    }


    public interface IRecyclerViewListener{
        void deleteIconClicked(DataServices.Forum forum);
        void likeClicked(boolean likeStatus , DataServices.Forum forum);
        void forumClicked(DataServices.Forum forum);
    }




}
