package com.example.inclass08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ForumDescription extends Fragment implements CommentAdapter.ICommentHolderListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public FirebaseAuth mAuth;
    public FirebaseFirestore db;
    ForumDescription forumDescription;
    CommentAdapter adapter;
    // TODO: Rename and change types of parameters
    private Forum currentForum;
    private CurrentUser user;

    public ForumDescription() {
        // Required empty public constructor
    }

    public static ForumDescription newInstance(Forum param1 , CurrentUser param2) {
        ForumDescription fragment = new ForumDescription();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentForum = (Forum) getArguments().getSerializable(ARG_PARAM1);
            user = (CurrentUser) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_forum_description, container, false);

        TextView forumName = view.findViewById(R.id.forumTitleDes);
        TextView forumOwner = view.findViewById(R.id.forumOwnerDes);
        TextView forumDes = view.findViewById(R.id.forumDesDes);
        TextView commentNumber = view.findViewById(R.id.numberCommentDes);
        TextView comment = view.findViewById(R.id.comment);
        RecyclerView recyclerView = view.findViewById(R.id.recycle);
        forumDescription = this;
        getActivity().setTitle(getResources().getString(R.string.forum));
        final TextView postComment = view.findViewById(R.id.writeComment);
        Button post = view.findViewById(R.id.post);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        forumName.setText(currentForum.title);
        forumOwner.setText(currentForum.owner);
        forumDes.setText(currentForum.description);
        commentNumber.setText(currentForum.comments.size()+"");
        if(currentForum.comments.size() > 1){
            comment.setText(getResources().getString(R.string.comments));
        } else {
            comment.setText(getResources().getString(R.string.comment));
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new CommentAdapter(currentForum.comments, user, forumDescription);
        recyclerView.setAdapter(adapter);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Comment = postComment.getText().toString();
                String Name = user.name;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String format = simpleDateFormat.format(new Date());
                String Date = format;
                String Email = user.email;

                Comment comment1 = new Comment(Name,Comment,Date,Email);
                currentForum.getComments().add(comment1);

                db.collection("Forums")
                        .document(currentForum.getId())
                        .update("Comments",currentForum.getComments())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    getData();
                                }
                            }
                        });
            }
        });

        return view;
    }

    @Override
    public void deleteCommentClicked(int position) {
        currentForum.getComments().remove(position);

        db.collection("Forums")
                .document(currentForum.getId())
                .update("Comments",currentForum.getComments())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getData();
                    }
                });
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


    public void getData() {
        db.collection("Forums")
            .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot forum : task.getResult()){
                                if(forum.getId() == currentForum.getId()){
                                    currentForum = (Forum) forum.getData();

                                }
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView view = getView().findViewById(R.id.numberCommentDes);
                                TextView view1 = getView().findViewById(R.id.comment);
                                view.setText(currentForum.comments.size()+"");
                                if(currentForum.comments.size() > 1){
                                    view1.setText(getResources().getString(R.string.comments));
                                } else {
                                    view1.setText(getResources().getString(R.string.comment));
                                }
                            }
                        });
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}