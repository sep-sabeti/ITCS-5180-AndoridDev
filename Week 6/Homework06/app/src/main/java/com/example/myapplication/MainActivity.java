package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Login.ILoginListener, RegisterAccount.IRegisterAccountListener, AppCategories.IAppCategoriesListener, TopPaidApps.ITopPaidAppsListener {

    //public booleans and parameters for navigating through fragments
    public boolean loginStatus = false;
    public String currentAccountName = null;
    public boolean createNewAccountClicked = false;
    public boolean registeredAccountStatus = false;
    public boolean logOutStatus = false;
    public String selectedAppCategory = null;
    public DataServices.App selectedAppDetail = null;
    public boolean goToAppDetailStatus = false;
    public boolean someThingWentWrong = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Starting the app by calling the login fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new Login())
                .commit();
        Log.d("MainActivity", "App loaded, new login fragment loaded");
        setTitle(R.string.login);
    }

    @Override
    public void getLoginButtonStatus(boolean status) {
        loginStatus = status;

    }

    @Override
    public void getLoggedInAccount(String token) {
        currentAccountName = token;
        if(loginStatus && currentAccountName!= null){
            setTitle(R.string.appCategories);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,  AppCategories.newInstance(currentAccountName),null)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void getCreateNewAccountButtonStatus(boolean status) {
        createNewAccountClicked = status;
        if(createNewAccountClicked){
            setTitle(R.string.register);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,  new RegisterAccount(),null)
                    .commit();
        }
    }

    @Override
    public void registeredAccountStatus(boolean status) {
        registeredAccountStatus = status;
        if(registeredAccountStatus){
            setTitle(R.string.appCategories);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,  AppCategories.newInstance(currentAccountName))
//                    .addToBackStack(null)
                    .commit();
        } else {
            setTitle(R.string.login);
            loginStatus = false;
            currentAccountName = null;
            createNewAccountClicked = false;
            registeredAccountStatus = false;
            logOutStatus = false;
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Login())
                    .commit();
        }

    }

    @Override
    public void registeredAccount(String token) {
        currentAccountName = token;

    }

    @Override
    public void logOutClicked(boolean status) {
        logOutStatus = status;
        if(logOutStatus){
            setTitle(R.string.login);
             loginStatus = false;
             currentAccountName = null;
            createNewAccountClicked = false;
            registeredAccountStatus = false;
            logOutStatus = false;
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Login())
                    .commit();
        }

    }

    @Override
    public void mAppCategoriesListener(String appCategory) {
        selectedAppCategory = appCategory;

        if(selectedAppCategory != null){
            setTitle(R.string.topPaidApps);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TopPaidApps.newInstance(currentAccountName,selectedAppCategory))
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void someThingWentWrong(boolean status) {
        someThingWentWrong = status;
        if(someThingWentWrong){
            setTitle(R.string.login);
            loginStatus = false;
            currentAccountName = null;
            createNewAccountClicked = false;
            registeredAccountStatus = false;
            logOutStatus = false;
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Login())
                    .commit();
        }
    }

    @Override
    public void selectedApp(DataServices.App app) {
        selectedAppDetail = app;
    }

    @Override
    public void goToAppDetailStatus(boolean status) {
        goToAppDetailStatus = status;
        if(goToAppDetailStatus && selectedAppDetail != null){
            setTitle(R.string.appDetails);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AppDetails.newInstance(selectedAppDetail))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void wentWrong(boolean status) {
        someThingWentWrong = status;
        if(someThingWentWrong){
            setTitle(R.string.login);
            loginStatus = false;
            currentAccountName = null;
            createNewAccountClicked = false;
            registeredAccountStatus = false;
            logOutStatus = false;
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Login())
                    .commit();
        }
    }
}