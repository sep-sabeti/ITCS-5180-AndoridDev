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


public class Login extends Fragment {


    ILoginFragmentListener mlistener;
    DataServices.AuthResponse account = null;
    boolean clickable = true;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final TextView email,password;
        Button login,createNewAccount;
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.loginButton);
        createNewAccount = view.findViewById(R.id.createNewAccountButton);

        getActivity().setTitle(R.string.login);
        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickable){
                    mlistener.createNewAccountClicked(true);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("")){
                    if(!password.getText().toString().equals("")){
                        if(clickable){
                            LoginAsync loginAsync = new LoginAsync();
                            loginAsync.execute();
                        }

                    } else{
                        Toast.makeText(getContext(), getResources().getString(R.string.missingPassword), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.missingEmail), Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ILoginFragmentListener){
            mlistener = (ILoginFragmentListener)context;
        }
    }

    public interface ILoginFragmentListener{
        void loginStatus(DataServices.AuthResponse account);
        void createNewAccountClicked(boolean status);
    }

    public class LoginAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;

            getView().findViewById(R.id.createNewAccountButton).setEnabled(false);
            getView().findViewById(R.id.loginButton).setEnabled(false);
            Toast.makeText(getContext(), getResources().getString(R.string.wait), Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            clickable = true;

            if(account != null){
                getView().findViewById(R.id.createNewAccountButton).setEnabled(true);
                getView().findViewById(R.id.loginButton).setEnabled(true);
                mlistener.loginStatus(account);
            } else {
                getView().findViewById(R.id.createNewAccountButton).setEnabled(true);
                getView().findViewById(R.id.loginButton).setEnabled(true);
                Toast.makeText(getContext(), getResources().getString(R.string.someThingWentWrong), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TextView emailInput = getView().findViewById(R.id.email);
            TextView passwordInput = getView().findViewById(R.id.password);
            try {
                account = DataServices.login(emailInput.getText().toString(),passwordInput.getText().toString());

        } catch (DataServices.RequestException ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }
}