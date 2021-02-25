package com.example.inclass05;

import android.content.Context;
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

public class RegisterFragment extends Fragment {
    private static final String CALLBACK = "Callback";
    private Callback callbackFragment;
    private ICreatedNewAccountListener mCreatedNewAccountListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(Callback callback) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putSerializable(CALLBACK, callback);
        fragment.setArguments(args);
        return fragment;
    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.callbackFragment = (Callback) getArguments().getSerializable(CALLBACK);
        }
        getActivity().setTitle(getResources().getString(R.string.register));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(getResources().getString(R.string.register));

        final EditText emailAddress = view.findViewById(R.id.emailRegisterFragment);
        final EditText passWord = view.findViewById(R.id.passwordRegisterFragment);
        final EditText name = view.findViewById(R.id.nameRegisterFragment);
         Button submit = view.findViewById(R.id.submitRegisterFragment);
         Button cancel = view.findViewById(R.id.cancelRegisterFragment);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailAddress.getText().toString();
                String inputName = name.getText().toString();
                String pass = passWord.getText().toString();

                if(!inputName.equals("")){
                    if(!email.equals("") && isEmailValid(email)){
                        if(!pass.equals("")){
                            try{
                                DataServices.register(inputName,email,pass,callbackFragment.new AuthorError());
                                if(callbackFragment.accountKeyGetter() != null){
                                    Toast.makeText(getContext(),getResources().getString(R.string.Toast_successfulCreation),Toast.LENGTH_SHORT).show();
                                    mCreatedNewAccountListener.createdNewAccountStatus(true);
                                    mCreatedNewAccountListener.createdNewAccountKey(callbackFragment.accountKeyGetter());
                                } else {
                                    Toast.makeText(getContext(),getResources().getString(R.string.Toast_wentWrong),Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(),getResources().getString(R.string.Toast_wentWrong),Toast.LENGTH_SHORT).show();
                            }

                        } else{
                            Toast.makeText(getContext(),getResources().getString(R.string.Toast_inputPassword),Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(getContext(),getResources().getString(R.string.Toast_inputEmail),Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(),getResources().getString(R.string.Toast_inputName),Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreatedNewAccountListener.createdNewAccountStatus(false);
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ICreatedNewAccountListener){
            mCreatedNewAccountListener = (ICreatedNewAccountListener)context;
        }
        getActivity().setTitle(getResources().getString(R.string.register));
    }

    public interface ICreatedNewAccountListener{
        void createdNewAccountStatus(boolean status);
        void createdNewAccountKey(String key);
    }
}