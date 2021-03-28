package com.example.midterm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class Forums extends Fragment implements ForumAdapter.IRecyclerViewListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Account = "account";
    public ArrayList<DataServices.Forum> forums = new ArrayList<>();
    public ForumAdapter adapter;
    public ForumAdapter.IRecyclerViewListener mListener;
    public DataServices.Forum toBeDeletedForum;
    public DataServices.Forum toBeLikedForum;
    IForumListener mListenrForum;
    boolean clickable ;

    // TODO: Rename and change types of parameters
    private DataServices.AuthResponse account;

    public Forums() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Forums newInstance(DataServices.AuthResponse account) {
        Forums fragment = new Forums();
        Bundle args = new Bundle();
        args.putSerializable(Account, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = (DataServices.AuthResponse) getArguments().getSerializable(Account);
            mListener = this;
            GetAllForumsAsync getAllForumsAsync = new GetAllForumsAsync();
            getAllForumsAsync.execute();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView;
        View view = inflater.inflate(R.layout.fragment_forums, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new ForumAdapter(forums , account , mListener );
        recyclerView.setAdapter(adapter);

        getActivity().setTitle(R.string.forums);

        view.findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(clickable){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.confirmation))
                            .setMessage(getResources().getString(R.string.sure))
                            .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mListenrForum.clickedLogout(true);

                                }
                            }).create().show();

                }

            }
        });

        view.findViewById(R.id.newForumButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(clickable){
                    mListenrForum.newForumClicked(true);
                }
            }
        });


        return view;
    }

    @Override
    public void deleteIconClicked(DataServices.Forum forum) {
        toBeDeletedForum = forum;
        if(clickable){
            DaleteForumAsync daleteForumAsync = new DaleteForumAsync();
            daleteForumAsync.execute();
        }
    }

    @Override
    public void likeClicked(boolean likeStatus, DataServices.Forum forum) {
        toBeLikedForum = forum;
        if(toBeLikedForum != null && likeStatus == false && clickable ){
            LikeForumAsync likeForumAsync = new LikeForumAsync();
            likeForumAsync.execute();

        } else if(toBeLikedForum != null && likeStatus == true && clickable){
            UnlikeForumAsync unlikeForumAsync = new UnlikeForumAsync();
            unlikeForumAsync.execute();

        }

    }

    @Override
    public void forumClicked(DataServices.Forum forum) {
        if(forum != null && clickable){
            mListenrForum.clickedForum(forum);
        }

    }



    public class LikeForumAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();
            clickable = false;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clickable = true;
            GetAllForumsAsyncAfterDeleteOrLike getAllForumsAsync = new GetAllForumsAsyncAfterDeleteOrLike();
            getAllForumsAsync.execute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                DataServices.likeForum(account.getToken(),toBeLikedForum.getForumId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class UnlikeForumAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clickable = true;
            GetAllForumsAsyncAfterDeleteOrLike getAllForumsAsync = new GetAllForumsAsyncAfterDeleteOrLike();
            getAllForumsAsync.execute();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();
            clickable = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                DataServices.unLikeForum(account.getToken(),toBeLikedForum.getForumId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class GetAllForumsAsyncAfterDeleteOrLike extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(forums != null) {
                adapter = new ForumAdapter(forums , account , mListener);
                RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
                recyclerView.setAdapter(adapter);
                clickable = true;
            } else {

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                forums = DataServices.getAllForums(account.getToken());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    public class GetAllForumsAsync extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(forums != null){
                adapter = new ForumAdapter(forums , account , mListener);
                RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
                recyclerView.setAdapter(adapter);
                clickable = true;
            } else {

            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                forums = DataServices.getAllForums(account.getToken());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class DaleteForumAsync extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clickable = true;
            GetAllForumsAsyncAfterDeleteOrLike getAllForumsAsync = new GetAllForumsAsyncAfterDeleteOrLike();
            getAllForumsAsync.execute();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();
            clickable = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                DataServices.deleteForum(account.getToken(),toBeDeletedForum.getForumId());
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IForumListener){
            mListenrForum = (IForumListener)context;
        }

    }

    public interface IForumListener{
         void clickedForum(DataServices.Forum forum);
         void clickedLogout(boolean status);
         void newForumClicked(boolean status);
    }


}