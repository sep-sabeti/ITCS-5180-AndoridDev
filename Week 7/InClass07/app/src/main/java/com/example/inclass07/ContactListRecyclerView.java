package com.example.inclass07;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListRecyclerView extends RecyclerView.Adapter<ContactListRecyclerView.ContactCardViewHolder> {
    ArrayList<Contact> contacts;
    IContactListRecyclerViewHolderListener mListener;

    public ContactListRecyclerView(ArrayList<Contact> contacts , IContactListRecyclerViewHolderListener mListener){
        this.contacts = contacts;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ContactCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card_holder,parent,false);
        ContactCardViewHolder holder = new ContactCardViewHolder(view ,  mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactCardViewHolder holder, final int position) {

        holder.contact = contacts.get(position);
        holder.textView.setText(contacts.get(position).name);
        holder.textView2.setText(contacts.get(position).id);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mListener.deleteButtonClicked(contacts.get(position).id);
            }
        });

    }


    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public static class ContactCardViewHolder extends RecyclerView.ViewHolder{
        TextView textView , textView2;
        Button button;
        Contact contact;
        IContactListRecyclerViewHolderListener mListener;
            public ContactCardViewHolder(@NonNull View itemView , final IContactListRecyclerViewHolderListener mListener) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
                textView2 = itemView.findViewById(R.id.textView2);
                button = itemView.findViewById(R.id.deleteButton);
                this.mListener = mListener;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.itemClicked(contact.id);
                    }
                });
            }

        }


        public interface IContactListRecyclerViewHolderListener{
        void deleteButtonClicked(String id);
        void itemClicked(String id);
        }
}
