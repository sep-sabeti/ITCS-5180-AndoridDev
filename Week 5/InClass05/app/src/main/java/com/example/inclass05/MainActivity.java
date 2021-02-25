package com.example.inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginLoginListener, AppCategoriesFragment.IAppCategoryClickingListener, TopPaidAppsFragment.ISelectedAppListener, RegisterFragment.ICreatedNewAccountListener {

    public static final String LOGIN = "Login";
    public static final String APP_CATEGORIES = "App Categories";
    public static final String REGISTER = "Register Account";
    public static final String TOP_PAID_APPS = "Top paid apps";
    public static final String APP_DETAIL_FRAGMENT = "App Detail Fragment";

    //Attributes for managing the clicking buttons in login fragment
    public boolean loginLogInFragmentClicked = false;
    public boolean loginStatusLogInFragment = false;
    public boolean createNewAccountLogInFragmentClicked = false;
    public boolean createNewAccountLogInFragmentStatus = false;
    public boolean logOutClicked = false;
    public String currentClickedAppCategory = "";
    public String currentAccountKey;
    public DataServices.Account currentAccount;
    public Callback currentCallback ;
    public DataServices.App selectedAppFromAppCategory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCallback = new Callback(this);
        //Starting the app with the Login Fragment
        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,LoginFragment.newInstance(currentCallback) , LOGIN)
                .commit();
    }

    @Override
    public void loginClicked(boolean status) {
        loginLogInFragmentClicked = status;

    }

    @Override
    public void loginStatus(boolean status) {
        loginStatusLogInFragment = status;
    }

    @Override
    public void createNewAccountClicked(boolean status) {
        createNewAccountLogInFragmentClicked = status;
        if(createNewAccountLogInFragmentClicked){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,RegisterFragment.newInstance(currentCallback),REGISTER)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void loggedInAccount(String accountKey) {
        currentAccountKey = accountKey;
        DataServices.getAccount(currentAccountKey, currentCallback.new AccountResponse());
        currentAccount = currentCallback.accountGetter();

        if(loginLogInFragmentClicked && loginStatusLogInFragment){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,AppCategoriesFragment.newInstance(currentAccount,currentAccountKey, currentCallback),APP_CATEGORIES)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void clickedAppCategory(String appCategory) {
        currentClickedAppCategory = appCategory;
        if(!currentClickedAppCategory.equals("")){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TopPaidAppsFragment.newInstance(currentCallback,currentClickedAppCategory,currentAccountKey),TOP_PAID_APPS)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void logOutClicked(boolean status) {
        logOutClicked = status;
        if(logOutClicked){
            Toast.makeText(MainActivity.this,getResources().getString(R.string.Toast_loggedOutSuccessfully),Toast.LENGTH_SHORT).show();
//            currentCallback = new Callback(getApplicationContext());
            currentCallback.accountKeySetter();
            currentCallback.accountSetter();
            currentAccount = null;
            currentAccountKey = null;
            currentClickedAppCategory = null;
            selectedAppFromAppCategory = null;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance(currentCallback),LOGIN)
                    .commit();
        }
    }

    @Override
    public void selectedApp(DataServices.App app) {
        selectedAppFromAppCategory =  app;
        if(selectedAppFromAppCategory != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,AppDetailFragment.newInstance(selectedAppFromAppCategory),APP_DETAIL_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void createdNewAccountStatus(boolean status) {
        createNewAccountLogInFragmentStatus = status;
        if(!createNewAccountLogInFragmentStatus){
            Toast.makeText(MainActivity.this,getResources().getString(R.string.Toast_canceledTransaction),Toast.LENGTH_SHORT).show();
                currentCallback.accountKeySetter();
                currentCallback.accountSetter();
                currentAccount = null;
                currentAccountKey = null;
                currentClickedAppCategory = null;
                selectedAppFromAppCategory = null;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new LoginFragment() , LOGIN)
                        .commit();
        }
    }

    @Override
    public void createdNewAccountKey(String key) {
        currentAccountKey = key;
        DataServices.getAccount(currentAccountKey,currentCallback.new AccountResponse());
        currentAccount = currentCallback.accountGetter();

        if(createNewAccountLogInFragmentStatus){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,AppCategoriesFragment.newInstance(currentAccount,currentAccountKey, currentCallback),APP_CATEGORIES)
                    .addToBackStack(null)
                    .commit();
        }
    }
}