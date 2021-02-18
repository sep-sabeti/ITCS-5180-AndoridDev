package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Login.ILogInStatus , RegisterFragment.IRegisterStasus , Account.IUpdateAccount , UpdateAccount.IUpdateAccount  {

    public static final String LOGIN_FRAGMENT = "Login";
    public static final String REGISTER_FRAGMENT = "Register";
    public static final String ACCOUNT_FRAGMENT = "Account";
    public static final String UPDATE_FRAGMENT = "Update";
    DataServices.Account currentAccout = null;


    boolean loginClicked = false;
    boolean createAccountClicked = false;
    boolean registeredSuccess = false;
    boolean registeredStatus = false;
    boolean isUpdateAccountClicked = false;
    boolean logOutClicked = false;
    boolean updateAccountStatus = false;
    boolean cancelUpdateAccountClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.login));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new Login(), LOGIN_FRAGMENT)
                .commit();
    }

    @Override
    public void crateNewAccountStatus(boolean status) {
        createAccountClicked = status;
        if(createAccountClicked){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new RegisterFragment(), REGISTER_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void loggedinAccount(DataServices.Account account) {
        currentAccout = account;
    }

    @Override
    public void loggedInStatus(boolean status) {
        loginClicked = status;
        if(loginClicked){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Account.newInstance(currentAccout), ACCOUNT_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void registerSuccess(boolean status) {
        registeredSuccess = status;
    }

    @Override
    public void registeredAccount(DataServices.Account account) {
        if(registeredSuccess && registeredStatus){
            currentAccout = account;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Account.newInstance(currentAccout), ACCOUNT_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        } else {
            currentAccout = null;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Login(), LOGIN_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void registeredStats(boolean status) {
        registeredStatus = status;
    }

    @Override
    public void updateAccountClicked(boolean status) {
        isUpdateAccountClicked = status;
        if((loginClicked && isUpdateAccountClicked) || (registeredStatus && registeredSuccess && isUpdateAccountClicked)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,UpdateAccount.newInstance(currentAccout),UPDATE_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void updateAccountStatus(boolean status) {
        updateAccountStatus = status;

    }

    @Override
    public void cancelUpdateClicked(boolean status) {
        cancelUpdateAccountClicked = status;
        if(cancelUpdateAccountClicked){
            Toast.makeText(MainActivity.this,getResources().getString(R.string.cancelTransaction),Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,Account.newInstance(currentAccout))
                    .commit();
        }

    }

    @Override
    public void updatedAccountGetter(DataServices.Account account){
        if(updateAccountStatus){
            currentAccout = account;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,Account.newInstance(currentAccout))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void logOutAccountClicked(boolean status) {
        logOutClicked = status;
        if(loginClicked){
            currentAccout = null;
            Toast.makeText(MainActivity.this,getResources().getString(R.string.loggOut),Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Login(), LOGIN_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }
}





