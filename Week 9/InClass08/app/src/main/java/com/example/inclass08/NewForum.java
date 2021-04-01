package com.example.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class NewForum extends Fragment {


    public FirebaseAuth mAuth;

    INewForumListner mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);

        final TextView title = view.findViewById(R.id.titleForum);
        final TextView description = view.findViewById(R.id.des);
        Button submit = view.findViewById(R.id.submitForum);
        Button cancel = view.findViewById(R.id.cancelForum);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelForumClicked(true);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleString = title.getText().toString();
                String desString = description.getText().toString();

                if(!titleString.equals("")){
                    if(!desString.equals("")){

                        FirebaseFirestore db = FirebaseFirestore.getInstance();


                        HashMap<String,Object> forum = new HashMap<>();
                        mAuth = FirebaseAuth.getInstance();



                        forum.put("Date",new Timestamp(new java.util.Date()));
                        forum.put("Description",desString);
                        forum.put("Name",titleString);
                        forum.put("Owner","Sep");
                        forum.put("Owner Email",mAuth.getCurrentUser().getEmail()) ;

                        db.collection("Forums")
                                .add(forum)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        mListener.newForumSubmit(true);
                                    }
                                });

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.emptyDes), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.emptyTitle), Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof INewForumListner){
            mListener = (NewForum.INewForumListner)context;
        }
    }

    public interface INewForumListner{
        void newForumSubmit(boolean status);
        void  cancelForumClicked(boolean status);
    }
}