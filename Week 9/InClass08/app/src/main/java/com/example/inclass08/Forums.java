package com.example.inclass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.provider.Settings.NameValueTable.VALUE;

public class Forums extends Fragment {


    private static final String NAME = "name";

    private String name;
    public ArrayList<Forum> forums = new ArrayList<>();
    public FirebaseAuth mAuth;
    IForumsListner mListner;

    public static Forums newInstance(String param1) {
        Forums fragment = new Forums();
        Bundle args = new Bundle();
        args.putString(NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Forums() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums, container, false);

        getActivity().setTitle(R.string.forums);
        Log.d("hey2", "onCreateView: " + name);
        Button logOut = view.findViewById(R.id.logout);
        Button newForum = view.findViewById(R.id.newForumButton);
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                mListner.logOutClicked(true);
            }
        });

        newForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListner.newForomClicked(true);
            }
        });



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Forums")
                .get()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot forum: task.getResult()
                            ) {
                                try{
                                    Forum forum1 = new Forum(forum.getData().get("Name").toString(),forum.getData().get("Owner").toString(),forum.getData().get("Description").toString(),forum.getId(),forum.getData().get("Owner Email").toString(), getDate(forum.getTimestamp("Date").getSeconds()));
                                    Log.d("TAG", "onComplete: "+forum1.toString());
                                    forums.add(forum1);
                                }
                                catch (NullPointerException e){

                                }
                            }
                        }
                    }
                });
        final Adapter adapter = new Adapter(forums);
        recyclerView.setAdapter(adapter);

        db.collection("Forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();
                        for (QueryDocumentSnapshot forum: value
                        ) {
                                try{
                                    Forum forum1 = new Forum(forum.getData().get("Name").toString(),forum.getData().get("Owner").toString(),forum.getData().get("Description").toString(), forum.getId() ,forum.getData().get("Owner Email").toString(),getDate(forum.getTimestamp("Date").getSeconds()));
                                    forums.add(forum1);
                                    adapter.notifyDataSetChanged();

                                } catch (NullPointerException e){

                                }
                        }
                    }
                });


        return view;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IForumsListner){
            mListner = (IForumsListner)context;
        }
    }

    public interface IForumsListner{
        void logOutClicked(boolean status);
        void newForomClicked(boolean status);
    }


}