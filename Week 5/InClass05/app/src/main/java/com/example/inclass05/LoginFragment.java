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

public class LoginFragment extends Fragment {

    ILoginLoginListener mILoginLoginListener;
    String accountKey;
    private static final String CALLBACK = "Callback";
    private Callback callbackFragment;

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(Callback callback) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(CALLBACK, callback);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.callbackFragment = (Callback) getArguments().getSerializable(CALLBACK);
        }
        getActivity().setTitle(getResources().getString(R.string.login));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ILoginLoginListener){
            mILoginLoginListener = (ILoginLoginListener)context;
        }
        getActivity().setTitle(getResources().getString(R.string.login));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        final EditText email = view.findViewById(R.id.emailLoginFragment);
        final EditText password = view.findViewById(R.id.passwordLoginFragment);
        final Button loginButton = view.findViewById(R.id.loginButtonLoginFragment);
        final Button createNewAccountButton = view.findViewById(R.id.createNewAccountLoginFragment);

        getActivity().setTitle(getResources().getString(R.string.login));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = email.getText().toString();
                String passwordNumber = password.getText().toString();
                mILoginLoginListener.loginClicked(true);

                if (!emailAddress.equals("") && isEmailValid(emailAddress)) {
                    if (!passwordNumber.equals("")) {
                        try{
                            DataServices.login(emailAddress, passwordNumber,callbackFragment.new AuthorError());
                            if(callbackFragment.accountKeyGetter() != null){
                                mILoginLoginListener.loginStatus(true);
                                accountKey = callbackFragment.accountKeyGetter();
                                mILoginLoginListener.loggedInAccount(accountKey);
                                Toast.makeText(getContext(),getResources().getString(R.string.Toast_loggedInSuccessfully),Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(),getResources().getString(R.string.Toast_credentialIncorrect),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception exception){
                            Toast.makeText(getContext(),getResources().getString(R.string.Toast_credentialIncorrect),Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(),getResources().getString(R.string.Toast_passWordWrong),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(),getResources().getString(R.string.Toast_emailInputWrong),Toast.LENGTH_SHORT).show();
                }
            }
        });

        createNewAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mILoginLoginListener.createNewAccountClicked(true);
            }
        });
        return view;
    }


    public interface ILoginLoginListener {
        void loginClicked(boolean status);
        void loginStatus(boolean status);
        void createNewAccountClicked(boolean status);
        void loggedInAccount(String accountKey);
    }
}