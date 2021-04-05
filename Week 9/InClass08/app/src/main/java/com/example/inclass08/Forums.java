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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Forums extends Fragment implements Adapter.ICardHolderListener {

    private static final String ARG_PARAM1 = "param1";
    private String name;
    public ArrayList<Forum> forums = new ArrayList<>();
    public FirebaseAuth mAuth;
    IForumsListner mListner;
    Forums ctx;
    Adapter adapter;
    RecyclerView recyclerView;
    FirebaseFirestore db;

    // TODO: Rename and change types of parameters
    private CurrentUser user;

    public Forums() {
        // Required empty public constructor
    }

    public static Forums newInstance(CurrentUser user) {
        Forums fragment = new Forums();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (CurrentUser) getArguments().getSerializable(ARG_PARAM1);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getActivity().setTitle(R.string.forums);
        Button logOut = view.findViewById(R.id.logout);
        Button newForum = view.findViewById(R.id.newForumButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
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

        ctx = this;
        adapter = new Adapter(forums ,  ctx);
        recyclerView.setAdapter(adapter);
        name = user.name;
        getData();
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

    @Override
    public void deleteClicked(final Forum forum) {

        Log.d("TAG", "deleteClicked: "+forum);
        db.collection("Forums").document(forum.getId()).delete();

    }

    @Override
    public void itemClicked(Forum forum) {
        if(forum.id != null){
            mListner.forumClicked(forum);
        }

    }


    public interface IForumsListner{
        void logOutClicked(boolean status);
        void newForomClicked(boolean status);
        void forumClicked(Forum forum);
    }


    public void getData(){
        db.collection("Forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();
                        for (QueryDocumentSnapshot forum: value
                        ) {
                            try{
                                Forum forum1 = new Forum(forum.getData().get("Title ").toString(),forum.getData().get("Owner").toString(),forum.getData().get("Description").toString(), forum.getId() ,forum.getData().get("Owner Email").toString(),getDate(forum.getTimestamp("Date").getSeconds()));
                                try{
                                    forum1.setComments((ArrayList<Comment>) forum.getData().get("Comments"));
                                    Log.d("TAG", "onEvent: "+forum.getData().get("Comments"));
                                } catch (java.lang.ClassCastException e){
                                    Log.d("TAG", "onEvent: "+"No");

                                    forum1.setComments();
                                }
                                forum1.setLiked((ArrayList<String>) forum.getData().get("Liked"));
                                forums.add(forum1);
                            } catch (NullPointerException e){
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}