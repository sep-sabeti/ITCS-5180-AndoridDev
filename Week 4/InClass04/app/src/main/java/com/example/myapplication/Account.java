package com.example.myapplication;

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

import java.io.Serializable;


public class Account extends Fragment {

    TextView nameView;
    private static final String ACCOUNT = "Account";
    DataServices.Account account;

    Button editProfile , logOut;
    IUpdateAccount mUpdateAccount;

    public Account() {
        // Required empty public constructor
    }

    public static Account newInstance(DataServices.Account account) {
        Account fragment = new Account();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT, (Serializable) account);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.account = (DataServices.Account) getArguments().getSerializable(ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        getActivity().setTitle(R.string.account);
        nameView = view.findViewById(R.id.nameAccountView);
        nameView.setText(account.getName());
        editProfile = view.findViewById(R.id.editProfileAccpuntButton);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateAccount.updateAccountClicked(true);
            }
        });

        logOut = view.findViewById(R.id.logOutAccountButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateAccount.logOutAccountClicked(false);
            }
        });

    return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IUpdateAccount){
            mUpdateAccount = (IUpdateAccount)context;
            getActivity().setTitle(R.string.account);

        } else {
            throw new RuntimeException(context.toString() + "Implement the interface");
        }
    }

    public interface IUpdateAccount {
        void updateAccountClicked(boolean status);
        void logOutAccountClicked(boolean status);
    }
}