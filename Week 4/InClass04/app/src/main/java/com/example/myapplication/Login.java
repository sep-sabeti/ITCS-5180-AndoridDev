package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends Fragment {

    String email = null;
    String password = null;
    ILogInStatus mIlogInStatus;

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
}

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailInput = (EditText)view.findViewById(R.id.emailInput);
                EditText passwordInput = (EditText)view.findViewById(R.id.passwordInput);
                if(!emailInput.getText().toString().equals("") && isEmailValid(emailInput.getText().toString())){
                    email = emailInput.getText().toString() ;
                    if(!passwordInput.getText().toString().equals("")){
                        password = passwordInput.getText().toString();
                            DataServices.Account accountLoggedIn = DataServices.login(email, String.valueOf(password));
                            if(accountLoggedIn !=null){
                                mIlogInStatus.loggedinAccount(accountLoggedIn);
                                mIlogInStatus.loggedInStatus(true);
                                Toast.makeText(getActivity(),getResources().getString(R.string.Toast_successful),Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(),getResources().getString(R.string.wrong),Toast.LENGTH_SHORT).show();
                            }
                        } else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.Toast_missingInput),Toast.LENGTH_SHORT).show();
                    }
                    } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.Toast_missingInput), Toast.LENGTH_SHORT).show();
                }
                }

        });

        view.findViewById(R.id.createNewAccountLoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.confirmation))
                        .setMessage(getResources().getString(R.string.sure))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mIlogInStatus.crateNewAccountStatus(true);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create().show();
            }
        });

        return view;
    }


    public interface ILogInStatus {
        void crateNewAccountStatus(boolean status);
        void loggedinAccount(DataServices.Account account);
        void loggedInStatus(boolean status);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ILogInStatus ){
            mIlogInStatus = (ILogInStatus) context;
            getActivity().setTitle(R.string.login);
        } else {
            throw new RuntimeException(context.toString() + "not fully implemented");
        }
    }
}