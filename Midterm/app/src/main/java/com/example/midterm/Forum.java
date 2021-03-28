package com.example.midterm;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Forum extends Fragment implements ForumCardAdapter.ICommentListener {


    private static final String FORUM = "forum";
    private static final String ACCOUNT = "account";


    // TODO: Rename and change types of parameters
    private DataServices.Forum forum;
    private DataServices.AuthResponse account;

    public ArrayList<DataServices.Comment> comments = new ArrayList<>();
    public ForumCardAdapter adapter;
    public ForumCardAdapter.ICommentListener mListner;
    public DataServices.Comment toBeDeleted;
    boolean clickable ;

    public Forum() {
        // Required empty public constructor
    }

    public static Forum newInstance(DataServices.Forum forum , DataServices.AuthResponse account) {
        Forum fragment = new Forum();
        Bundle args = new Bundle();
        args.putSerializable(FORUM, forum);
        args.putSerializable(ACCOUNT, account);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (DataServices.Forum) getArguments().getSerializable(FORUM);
            account = (DataServices.AuthResponse) getArguments().getSerializable(ACCOUNT);
            GetCommentsAsync getCommentsAsync = new GetCommentsAsync();
            getCommentsAsync.execute();
            mListner = this;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final TextView writeComment;
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        TextView forumTittle = view.findViewById(R.id.forumTitle);
        forumTittle.setText(forum.getTitle());

        TextView forumCreator = view.findViewById(R.id.forumOwnerinForum);
        forumCreator.setText(forum.getCreatedBy().getName());
        TextView forumDesc = view.findViewById(R.id.forumDescriptioninForum);
        forumDesc.setText(forum.getDescription());

        Button post = view.findViewById(R.id.postComment);

        writeComment = view.findViewById(R.id.writeComment);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.commentView);
        recyclerView.setLayoutManager(manager);
        adapter = new ForumCardAdapter(comments , account , mListner);
        recyclerView.setAdapter(adapter);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!writeComment.getText().toString().equals("")){
                        if(clickable){
                            PostAsync postAsync = new PostAsync();
                            postAsync.execute();
                        }

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.missingText), Toast.LENGTH_SHORT).show();
                    }
            }
        });

        return view;
    }

    @Override
    public void clickedComment(DataServices.Comment comment) {
        if(comment != null){
            toBeDeleted = comment;
            DeleteComment deleteComment = new DeleteComment();
            deleteComment.execute();
        }

    }



    public class DeleteComment extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();
            clickable = false;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GetCommentsAsyncAfterPost getCommentsAsyncAfterPost = new GetCommentsAsyncAfterPost();
            getCommentsAsyncAfterPost.execute();
            clickable = true;


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DataServices.deleteComment(account.getToken(),forum.getForumId(),toBeDeleted.getCommentId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class PostAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            GetCommentsAsyncAfterPost getCommentsAsyncAfterPost = new GetCommentsAsyncAfterPost();
            getCommentsAsyncAfterPost.execute();
            clickable = true;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                TextView writeComment = getView().findViewById(R.id.writeComment);
                DataServices.createComment(account.getToken(),forum.getForumId(),writeComment.getText().toString());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),getResources().getString(R.string.someThingWentWrong),Toast.LENGTH_SHORT).show();
            }

            return null;
        }
    }


    public class GetCommentsAsyncAfterPost extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(comments!=null ){
try{
    TextView forumComments = getView().findViewById(R.id.forumComments);
    forumComments.setText(comments.size() + " "+getResources().getString(R.string.comments));
    RecyclerView recyclerView = getView().findViewById(R.id.commentView);
    adapter = new ForumCardAdapter(comments , account , mListner);
    recyclerView.setAdapter(adapter);
} catch (Exception e){
    e.toString();
}


            }
            clickable = true;


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                comments = DataServices.getForumComments(account.getToken(),forum.getForumId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    public class GetCommentsAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(comments!=null ){
                TextView forumComments = getView().findViewById(R.id.forumComments);
                forumComments.setText(comments.size() + " "+getResources().getString(R.string.comments));
                RecyclerView recyclerView = getView().findViewById(R.id.commentView);
                adapter = new ForumCardAdapter(comments , account , mListner);
                recyclerView.setAdapter(adapter);

            }
            clickable = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                comments = DataServices.getForumComments(account.getToken(),forum.getForumId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}