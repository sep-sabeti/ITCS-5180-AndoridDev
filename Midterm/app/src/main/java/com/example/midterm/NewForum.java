package com.example.midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class NewForum extends Fragment {


    private static final String ACCOUNT = "account";
    String title, text;
    DataServices.Forum forum;
ICreateNewForumListener mListener;

boolean clickable = true;
    // TODO: Rename and change types of parameters
    private DataServices.AuthResponse account;

    public NewForum() {
        // Required empty public constructor
    }


    public static NewForum newInstance(DataServices.AuthResponse account) {
        NewForum fragment = new NewForum();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = (DataServices.AuthResponse) getArguments().getSerializable(ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final TextView forumName, forumDes;
        Button submit,cancel;

        View view = inflater.inflate(R.layout.fragment_new_forum, container, false);

        forumName = view.findViewById(R.id.forumTitle);
        forumDes = view.findViewById(R.id.forumDescription);
        submit = view.findViewById(R.id.submitNewForum);
        cancel = view.findViewById(R.id.cancelNewForum);

        getActivity().setTitle(R.string.newForum);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickable){
                    mListener.cancelNewForumClicked(true);
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(!forumName.getText().toString().equals("")){
                        if(!forumDes.getText().toString().equals("")){
                            title = forumName.getText().toString();
                            text = forumDes.getText().toString();
                            if(clickable){
                                CreateForum createForum = new CreateForum();
                                createForum.execute();
                            }
                        }else {
                            Toast.makeText(getContext(), getResources().getString(R.string.missingForumDescription), Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.missingForumName), Toast.LENGTH_SHORT).show();
                    }

            }
        });


        return view;
    }



    public class CreateForum extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.newForum(forum);
            clickable = true;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                forum = DataServices.createForum(account.getToken(),title,text);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ICreateNewForumListener){
            mListener = (ICreateNewForumListener)context;
        }
    }

    public interface ICreateNewForumListener{
        void cancelNewForumClicked(boolean status);
        void newForum(DataServices.Forum forum);
    }
}