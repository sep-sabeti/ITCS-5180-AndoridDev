package com.example.inclass08;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends RecyclerView.Adapter<Adapter.CardHolder> {

    ArrayList<Forum> forums;
    public FirebaseAuth mAuth;
    Adapter.ICardHolderListener context;
    public FirebaseFirestore db;

    public Adapter(ArrayList<Forum> forums , Adapter.ICardHolderListener context ){
        this.forums = forums;
        this.context = context;
    }


    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_card_holder,parent,false);
        CardHolder cardHolder = new CardHolder(view , context);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardHolder holder, final int position) {

        holder.name.setText(forums.get(position).title);
        holder.description.setText(forums.get(position).description);
        holder.date.setText(forums.get(position).date);
        holder.owner.setText(forums.get(position).owner);

        mAuth = FirebaseAuth.getInstance();
        if(!mAuth.getCurrentUser().getEmail().equals(forums.get(position).ownerEmail)){
            holder.imageView.setEnabled(false);
            holder.imageView.setVisibility(View.GONE);
            holder.imageView.setBackgroundColor(Color.parseColor("#80000000"));

        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ctx.itemClicked(forums.get(position));
            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ctx.deleteClicked(forums.get(position));
            }
        });

        db = FirebaseFirestore.getInstance();

            db.collection("Forums")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for(QueryDocumentSnapshot doc:task.getResult()){

                                if(doc.getData().get("Title ").equals(forums.get(position).title)){

                                    ArrayList<String> likedUsers = (ArrayList<String>) doc.getData().get("Liked");

                                    if(likedUsers.size()>0){
                                        if(likedUsers.contains(mAuth.getCurrentUser().getEmail())){
                                            holder.imageView2.setImageResource(R.drawable.like_favorite);
                                        }
                                    }

                                }
                            }

                        }
                    });


            holder.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("Forums")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    for(QueryDocumentSnapshot doc:task.getResult()){

                                        if(doc.getData().get("Title ").equals(forums.get(position).title)){

                                            ArrayList<String> likedUsers = (ArrayList<String>) doc.getData().get("Liked");

                                            if(likedUsers.size()>0){

                                                if(likedUsers.contains(mAuth.getCurrentUser().getEmail())){
                                                    HashMap<String,Object> newDoc = new HashMap<>();
                                                    likedUsers.remove(mAuth.getCurrentUser().getEmail());
                                                    newDoc.put("Liked",likedUsers);
                                                    db.collection("Forums")
                                                            .document(forums.get(position).getId())
                                                            .update(newDoc)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        holder.imageView2.setImageResource(R.drawable.like_not_favorite);
                                                                    }
                                                                }
                                                            });

                                                } else {

                                                    HashMap<String,Object> newDoc = new HashMap<>();
                                                    likedUsers.add(mAuth.getCurrentUser().getEmail());
                                                    newDoc.put("Liked",likedUsers);
                                                    db.collection("Forums")
                                                            .document(forums.get(position).getId())
                                                            .update(newDoc)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        holder.imageView2.setImageResource(R.drawable.like_favorite);
                                                                    }
                                                                }
                                                            });

                                                }
                                            } else {

                                                HashMap<String,Object> newDoc = new HashMap<>();
                                                likedUsers.add(mAuth.getCurrentUser().getEmail());
                                                newDoc.put("Liked",likedUsers);
                                                db.collection("Forums")
                                                        .document(forums.get(position).getId())
                                                        .update(newDoc)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    holder.imageView2.setImageResource(R.drawable.like_favorite);
                                                                }
                                                            }
                                                        });
                                            }

                                        }
                                    }

                                }
                            });
                }
            });

    }

    @Override
    public int getItemCount() {
        return this.forums.size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder{

        TextView date,name,description,owner;
        ImageView imageView , imageView2;
        Adapter.ICardHolderListener ctx;

        public CardHolder(@NonNull View itemView , Adapter.ICardHolderListener context) {
            super(itemView);
            date = itemView.findViewById(R.id.dateCard);
            name = itemView.findViewById(R.id.titleCard);
            description = itemView.findViewById(R.id.descCard);
            owner = itemView.findViewById(R.id.ownerCard);
            imageView = itemView.findViewById(R.id.imageView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            ctx = context;
        }
    }

    public interface ICardHolderListener {
        void deleteClicked(Forum forum);
        void itemClicked(Forum forum);
    }
}
