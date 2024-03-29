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
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class Login extends Fragment {
    
    ILoginListener mListener;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final TextView email , pass ;
        Button login,create;
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        
        email = view.findViewById(R.id.emailInput);
        pass = view.findViewById(R.id.passInput);
        
        login = view.findViewById(R.id.loginButton);
        create = view.findViewById(R.id.newAccountButton);
        getActivity().setTitle(R.string.login);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createNewAccountClicked(true);
            }
        });
        
     
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString();
                String passInput = pass.getText().toString();
                
                mAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();
                
                if(!emailInput.equals("")){
                    if(!passInput.equals("")){

                        mAuth.signInWithEmailAndPassword(emailInput,passInput)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            getCurrentUser();
                                        }
                                        else {
                                            Toast.makeText(getContext(), getResources().getString(R.string.someThing), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.emptyPass), Toast.LENGTH_SHORT).show();
                    }
                    
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.emptyEmail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        
        if(context instanceof ILoginListener){
            mListener = (ILoginListener)context;
        }
    }

    public void getCurrentUser(){
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                if(documentSnapshot.getData().get("Email").equals(mAuth.getCurrentUser().getEmail())){
                                    CurrentUser user = new CurrentUser(documentSnapshot.getData().get("Name").toString(),documentSnapshot.getData().get("Email").toString());
                                    Log.d(MainActivity.TAG, "onComplete: ");
                                    mListener.loginClicked(user);

                                }
                            }

                        }
                    }
                });
    }

    public interface ILoginListener {
        
        void loginClicked(CurrentUser user);
        void createNewAccountClicked(boolean status);
        
    }
}