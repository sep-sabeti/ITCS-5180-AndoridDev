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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateNewAccount extends Fragment {


    String Name,Pass,Email;

    DataServices.AuthResponse account;
    ICreateAccountListener mListener;
    boolean clickable = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final TextView name,email,password;
        Button submit,cancel;

        View view = inflater.inflate(R.layout.fragment_create_new_account, container, false);
        getActivity().setTitle(R.string.createNewAccount);

        name = view.findViewById(R.id.nameInput);
        email = view.findViewById(R.id.emailInput);
        password = view.findViewById(R.id.passwordInput);
        submit = view.findViewById(R.id.submitButton);
        cancel = view.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelClicked(true);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!name.getText().toString().equals("")){
                    if(!email.getText().toString().equals("")){
                        if(isEmailValid(email.getText().toString())){
                            if(!password.getText().toString().equals("")){
                                if(clickable){
                                    Name = name.getText().toString();
                                    Pass = password.getText().toString();
                                    Email = email.getText().toString();
                                    CreateNewAccountAsync createNewAccount = new CreateNewAccountAsync();
                                    createNewAccount.execute();
                                }
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.missingPassword), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.emailValidemail), Toast.LENGTH_SHORT).show();

                        }

                    } else{
                    Toast.makeText(getContext(), getResources().getString(R.string.missingEmail), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.missingName), Toast.LENGTH_SHORT).show();
                }
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


    public class CreateNewAccountAsync extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clickable = false;
            getView().findViewById(R.id.submitButton).setEnabled(false);
            getView().findViewById(R.id.cancelButton).setEnabled(false);
            Toast.makeText(getContext(),getResources().getString(R.string.wait),Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mListener.registeredAccount(account);
            clickable = true;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                account = DataServices.register(Name,Email,Pass);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ICreateAccountListener){
            mListener = (ICreateAccountListener)context;
        }
    }

    public interface ICreateAccountListener{
         void cancelClicked(boolean status);
        void registeredAccount(DataServices.AuthResponse account);
    }

}