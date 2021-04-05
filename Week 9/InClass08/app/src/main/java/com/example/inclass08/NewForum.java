package com.example.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewForum extends Fragment {


    public FirebaseAuth mAuth;
    public FirebaseFirestore db;
    String name;

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
                final String titleString = title.getText().toString();
                final String desString = description.getText().toString();

                if(!titleString.equals("")){
                    if(!desString.equals("")){


                        mAuth = FirebaseAuth.getInstance();
                        db = FirebaseFirestore.getInstance();
                        db.collection("Users")
                                .get()
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        for (QueryDocumentSnapshot doc: task.getResult()
                                             ) {
                                            String docEmail  = doc.getData().get("Email").toString();

                                            String docName = doc.getData().get("Name").toString();
                                            Log.d("TAG", "hey "+docEmail);
                                            Log.d("TAG", "hey "+mAuth.getCurrentUser().getEmail());

                                            if(docEmail.equals(mAuth.getCurrentUser().getEmail())){
                                                name = docName;
                                                HashMap<String,Object> forum = new HashMap<>();
                                                forum.put("Date",new Timestamp(new java.util.Date()));
                                                forum.put("Description",desString);
                                                forum.put("Title ",titleString);
                                                forum.put("Owner", name);
                                                forum.put("Owner Email",mAuth.getCurrentUser().getEmail()) ;
                                                forum.put("Liked",new ArrayList<String>());
                                                forum.put("Comments",new ArrayList<Map<String,Object>>());
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                db.collection("Forums")
                                                        .add(forum)
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                mListener.newForumSubmit(true);
                                                            }
                                                        });
                                            }
                                        }

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