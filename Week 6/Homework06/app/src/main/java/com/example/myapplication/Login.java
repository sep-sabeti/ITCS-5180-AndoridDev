package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Fragment {

    ILoginListener mLoginListener;
    private String loggedInAccount = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(R.string.login);

        Button login,createNewAccount;
        final EditText emailEditText, passwordEditText;

        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
        login = view.findViewById(R.id.loginButton);
        createNewAccount = view.findViewById(R.id.createNewAccountButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password =passwordEditText.getText().toString();
                if(!email.equals("")){
                    if(!password.equals("")){
                        try{
                        LoginAsync loginAsync = new LoginAsync();
                        loginAsync.execute(email,password);
                        } catch (Exception e){
                            Log.d("loginFragment", e.toString());
                        }
                    } else{
                        Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingPassword), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingEmail), Toast.LENGTH_SHORT).show();
                }

            };

        });


        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("loginFragment", "createNewAccount Clicked");
                mLoginListener.getCreateNewAccountButtonStatus(true);
            }
        });

        return view;
    }


    public class LoginAsync extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getView().findViewById(R.id.loginButton).setEnabled(false);
            getView().findViewById(R.id.createNewAccountButton).setEnabled(false);
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_loginAttempt), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            getView().findViewById(R.id.loginButton).setEnabled(true);
            getView().findViewById(R.id.createNewAccountButton).setEnabled(true);
            loggedInAccount = string;
            if(loggedInAccount != null && !loggedInAccount.equals("")){
                mLoginListener.getLoginButtonStatus(true);
                Toast.makeText(getContext(), getResources().getString(R.string.Toast_login), Toast.LENGTH_SHORT).show();
                mLoginListener.getLoggedInAccount(loggedInAccount);
            } else {
                mLoginListener.getLoginButtonStatus(false);
                Toast.makeText(getContext(), getResources().getString(R.string.Toast_wrongCredentials), Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(String... strings) {
            String emailInput = strings[0];
            String passwordInput = strings[1];
            String result = null;
            try {
                String token = DataServices.login(emailInput,passwordInput);
                if(!token.equals("") && token != null){
                    Log.d("hey", token);
                    result = token;
                } else {
                    throw new DataServices.RequestException("cannot find the account");
                }
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ILoginListener){
            mLoginListener = (ILoginListener)context;
        }
    }

    public interface ILoginListener{
        void getLoginButtonStatus(boolean status);
        void getLoggedInAccount(String token);
        void getCreateNewAccountButtonStatus(boolean status);

    }

}