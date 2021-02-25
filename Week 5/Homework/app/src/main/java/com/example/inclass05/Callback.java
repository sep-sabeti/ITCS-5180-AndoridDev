package com.example.inclass05;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Callback implements Serializable {
    private String accountKey;
    private Context context;
    private DataServices.Account Account;
    private ArrayList<Object> data;


    Callback(Context context){
        this.context = context;
    }

    public class AuthorError implements DataServices.AuthResponse{
        public AuthorError(){
        }

        @Override
        public void onSuccess(String token) {
            accountKey = token;
        }

        @Override
        public void onFailure(DataServices.RequestException exception) {
            accountKey = null;
        }
    }

    public class AccountResponse implements DataServices.AccountResponse{
        public AccountResponse(){
        }

        @Override
        public void onSuccess(DataServices.Account account) {
            Account = account;
        }

        @Override
        public void onFailure(DataServices.RequestException exception) {
        }

    }

    public class DataResponse implements DataServices.DataResponse{
        public DataResponse(){
        }

        @Override
        public void onSuccess(ArrayList data) {
            Callback.this.data = data;
        }

        @Override
        public void onFailure(DataServices.RequestException exception) {
        }
    }

    public String accountKeyGetter(){
        return this.accountKey;
    }

    public DataServices.Account accountGetter(){
        return this.Account;
    }

    public ArrayList<Object> getData(){
        return this.data;
    }

    public void accountKeySetter(){
        this.accountKey = null;
    }

    public void accountSetter(){
        this.Account = null;
    }

}
