package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class UpdateAccount extends Fragment {

    TextView textView;
    EditText name,pass;
    Button submit,cancel;
    IUpdateAccount mUpdateAccount;
    private static final String ACCOUNT = "Account";
    DataServices.Account oldAccount , updatedAccount;

    // TODO: Rename and change types of parameters
    private DataServices.Account account;

    public UpdateAccount() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static UpdateAccount newInstance(DataServices.Account account ) {
        UpdateAccount fragment = new UpdateAccount();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT, (Serializable) account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = (DataServices.Account) getArguments().getSerializable(ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getResources().getString(R.string.updateAccount));
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        textView = view.findViewById(R.id.emailUpdateAccountView);
        submit = view.findViewById(R.id.updateSubmitButton);
        cancel = view.findViewById(R.id.cancelUpdateButton);
        name = view.findViewById(R.id.editTextTextPersonName);
        pass = view.findViewById(R.id.emailUpdateAccountInput);
        textView.setText(account.getEmail());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!name.getText().toString().equals("")){
                    if(!pass.getText().toString().equals("")){
                        try{
                            oldAccount = account;
                            updatedAccount = DataServices.update(oldAccount,name.getText().toString(),pass.getText().toString());
                            if(updatedAccount != null){
                                Toast.makeText(getActivity(),getResources().getString(R.string.updateedAccountToast),Toast.LENGTH_SHORT).show();
                                mUpdateAccount.updateAccountStatus(true);
                                mUpdateAccount.updatedAccountGetter(updatedAccount);
                                Log.d("TAG", updatedAccount.getName());
                            } else {
                                Toast.makeText(getActivity(),getResources().getString(R.string.wrong),Toast.LENGTH_SHORT).show();
                                mUpdateAccount.updateAccountStatus(false);
                                mUpdateAccount.updatedAccountGetter(oldAccount);
                            }
                        } catch (RuntimeException e){
                            mUpdateAccount.updateAccountStatus(false);
                        }
                    } else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.inputWrong),Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),getResources().getString(R.string.inputWrong),Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.confirmation))
                        .setMessage(getResources().getString(R.string.sure_update))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               mUpdateAccount.cancelUpdateClicked(true);
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IUpdateAccount){
            mUpdateAccount = (IUpdateAccount)context;
            getActivity().setTitle(R.string.updateAccount);
        }
    }


    public interface IUpdateAccount{
        void updateAccountStatus(boolean status);
        void cancelUpdateClicked(boolean status);
        void updatedAccountGetter(DataServices.Account account);
    }
}