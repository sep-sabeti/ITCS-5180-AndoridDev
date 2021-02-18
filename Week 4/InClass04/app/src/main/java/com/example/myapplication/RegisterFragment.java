package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {
    EditText nameInputRegister, emailInputRegister,passwordInputHint;
    Button submitRegisterButton, cancelRegisterButton;
    String name, email, password;
    IRegisterStasus mRegisterStatus;


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getResources().getString(R.string.createNewAccount));


        View view = inflater.inflate(R.layout.fragment_register, container, false);

        nameInputRegister = (EditText) view.findViewById(R.id.nameInputRegister);
        emailInputRegister = (EditText) view.findViewById(R.id.emailInputRegister);
        passwordInputHint = (EditText) view.findViewById(R.id.passwordInputHint);
        submitRegisterButton = (Button) view.findViewById(R.id.submitRegisterButton);
        cancelRegisterButton = (Button) view.findViewById(R.id.cancelRegisterButton);

        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(nameInputRegister.getText().toString().equals(""))){
                    name = nameInputRegister.getText().toString();
                    if(!(emailInputRegister.getText().toString().equals("")) && isEmailValid(emailInputRegister.getText().toString())){
                        email = emailInputRegister.getText().toString();
                        if(!(passwordInputHint.getText().toString().equals(""))){
                            password = passwordInputHint.getText().toString();
                            DataServices.Account account = DataServices.register(name, email, password);
                            if(account != null){
                                mRegisterStatus.registeredStats(true);
                                mRegisterStatus.registerSuccess(true);
                                mRegisterStatus.registeredAccount(account);
                                Toast.makeText(getActivity(),getResources().getString(R.string.registerSuccess),Toast.LENGTH_SHORT).show();
                            } else{
                                Toast.makeText(getActivity(),getResources().getString(R.string.changeEmail),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getActivity(),getResources().getString(R.string.inputWrong),Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.inputWrong),Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity(),getResources().getString(R.string.inputWrong),Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterStatus.registerSuccess(false);
                mRegisterStatus.registeredStats(false);
                mRegisterStatus.registeredAccount(null);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IRegisterStasus){
            mRegisterStatus = (IRegisterStasus)context;
            getActivity().setTitle(R.string.register);
        } else{
            throw new RuntimeException(context.toString() + "Implement interface");
        }
    }

    public interface IRegisterStasus {
        void registerSuccess(boolean status);
        void registeredAccount(DataServices.Account account);
        void registeredStats(boolean status);
    }
}






























