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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateNewAccount extends Fragment {

    ICreateNewAccountListener mListener;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_new_account, container, false);

        final TextView name = view.findViewById(R.id.name);
        final TextView email = view.findViewById(R.id.email);
        final TextView password = view.findViewById(R.id.password);

        getActivity().setTitle(R.string.create);

        Button submit = view.findViewById(R.id.submitButton);
        Button cancel = view.findViewById(R.id.cancel);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!name.getText().toString().equals("")){
                    if(!email.getText().toString().equals("")){
                        if(isEmailValid(email.getText().toString())){
                            if(!password.getText().toString().equals("")){
                                mAuth = FirebaseAuth.getInstance();
                                db = FirebaseFirestore.getInstance();
                                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){

                                                    HashMap<String,Object> user = new HashMap<>();
                                                    user.put("Email",mAuth.getCurrentUser().getEmail());
                                                    user.put("Name",name.getText().toString());

                                                    db.collection("Users")
                                                            .add(user)
                                                            .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentReference>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                    if(task.isSuccessful()){
                                                                        CurrentUser user1 = new CurrentUser(name.getText().toString(),email.getText().toString());
                                                                        mListener.createdAccountStatus(user1);
                                                                        Toast.makeText(getContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(getContext(), getResources().getString(R.string.someThing), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });




                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.emptyPass), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.validEmail), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.emptyEmail), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.emptyName), Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreatedClicked(true);
            }
        });

        return view;
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ICreateNewAccountListener){
            mListener = (ICreateNewAccountListener)context;
        }
    }

    public interface ICreateNewAccountListener {

        void createdAccountStatus(CurrentUser user);
        void cancelCreatedClicked(boolean status);
    }
}