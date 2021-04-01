package com.example.inclass08;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.CardHolder> {

    ArrayList<Forum> forums;
    public FirebaseAuth mAuth;

    public Adapter(ArrayList<Forum> forums ){
        this.forums = forums;
    }


    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_card_holder,parent,false);
        CardHolder cardHolder = new CardHolder(view);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, final int position) {

        holder.name.setText(forums.get(position).title);
        holder.description.setText(forums.get(position).description);
        holder.date.setText(forums.get(position).date);
        holder.owner.setText(forums.get(position).owner);

        mAuth = FirebaseAuth.getInstance();
        Log.d("TAG", "onBindViewHolder1: "+ forums.get(position).ownerEmail);
        Log.d("TAG", "onBindViewHolder2: "+ mAuth.getCurrentUser().getEmail());

        if(!mAuth.getCurrentUser().getEmail().equals(forums.get(position).ownerEmail)){
            holder.imageView.setEnabled(false);
            holder.imageView.setVisibility(View.GONE);

        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser().getEmail().equals(forums.get(position).ownerEmail)){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Forums").document(forums.get(position).getId()).delete();
                    Log.d("TAG", "onClick: "+"clicked Yes");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.forums.size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder{

        TextView date,name,description,owner;
        ImageView imageView;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.dateCard);
            name = itemView.findViewById(R.id.titleCard);
            description = itemView.findViewById(R.id.descCard);
            owner = itemView.findViewById(R.id.ownerCard);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
