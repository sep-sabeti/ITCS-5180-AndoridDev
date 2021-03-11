package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterAccount extends Fragment {

    IRegisterAccountListener mRegisterAccountListener;
    private String registeredAccount = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_account, container, false);

        getActivity().setTitle(R.string.register);

        Button submit,cancel;
        final EditText emailInput;
        final EditText passwordInput ;
        final EditText nameInput ;

        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        passwordInput = view.findViewById(R.id.passwordInput);
        submit = view.findViewById(R.id.submitButton);
        cancel = view.findViewById(R.id.cancelButton);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.confrimation))
                        .setMessage(getResources().getString(R.string.confrimation_Message))
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mRegisterAccountListener.registeredAccountStatus(false);
                            }
                        }).create().show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String pass = passwordInput.getText().toString();

                if(!name.equals("")){
                    if(!email.equals("")){
                        if(isEmailValid(email)){
                            if(!pass.equals("")){
                                try{
                                    RegisterAccountAsync registerAccountAsync = new RegisterAccountAsync();
                                    registerAccountAsync.execute(name,email,pass);

                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingPassword), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.Toast_emailWrongPattern), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingEmail), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("TAG", "2");
                    Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingName), Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IRegisterAccountListener){
            mRegisterAccountListener = (IRegisterAccountListener)context;
        }
    }

    public interface IRegisterAccountListener{
        void registeredAccountStatus(boolean status);
        void registeredAccount(String token);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public class RegisterAccountAsync extends AsyncTask<String,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            getView().findViewById(R.id.submitButton).setEnabled(false);
            getView().findViewById(R.id.cancelButton).setEnabled(false);
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_waitForRegistery), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getView().findViewById(R.id.submitButton).setEnabled(true);
            getView().findViewById(R.id.cancelButton).setEnabled(true);
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_registered), Toast.LENGTH_SHORT).show();
            if(registeredAccount != null &&!registeredAccount.equals("")){
                mRegisterAccountListener.registeredAccount(registeredAccount);
                mRegisterAccountListener.registeredAccountStatus(true);
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            String name = strings[0];
            String email = strings[1];
            String pass = strings[2];

            try{
                String token = DataServices.register(name,email,pass);
                registeredAccount = token;

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }


}