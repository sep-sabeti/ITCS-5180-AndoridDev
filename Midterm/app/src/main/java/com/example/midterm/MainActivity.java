package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Login.ILoginFragmentListener , Forums.IForumListener, CreateNewAccount.ICreateAccountListener, NewForum.ICreateNewForumListener {

    //public booleans for controlling the app

    DataServices.AuthResponse currentAccount = null;
    boolean createNewAccountClicked = false;
    boolean backStackAvailable = true;
    DataServices.Forum clickedForum = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.login);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new Login() , "Login")
                .commit();


    }


    @Override
    public void loginStatus(DataServices.AuthResponse account) {
        currentAccount = account;
        if(currentAccount != null){
                setTitle(R.string.forums);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Forums.newInstance(currentAccount))
                        .commit();

        }

    }

    @Override
    public void createNewAccountClicked(boolean status) {
        createNewAccountClicked = status;
        if(createNewAccountClicked){
            setTitle(R.string.createNewAccount);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new CreateNewAccount(),"CreateNewAccount")
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        if(backStackAvailable){
            super.onBackPressed();

        } else {
        }

    }

    @Override
    public void clickedForum(DataServices.Forum forum) {
        clickedForum = forum;
        if(forum != null){
            setTitle(R.string.forum);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Forum.newInstance(clickedForum,currentAccount))
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void clickedLogout(boolean status) {
        if(status){
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Login())
                    .commit();
            Toast.makeText(this, getResources().getString(R.string.loggedOuyt), Toast.LENGTH_SHORT).show();
            DataServices.AuthResponse currentAccount = null;
            boolean createNewAccountClicked = false;
            boolean backStackAvailable = true;
            DataServices.Forum clickedForum = null;
        }
    }

    @Override
    public void newForumClicked(boolean status) {
        if(status){
            setTitle(R.string.newForum);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NewForum.newInstance(currentAccount))
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void cancelClicked(boolean status) {
        if (status){
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Login())
                    .commit();
            Toast.makeText(this, getResources().getString(R.string.loggedOuyt), Toast.LENGTH_SHORT).show();
            DataServices.AuthResponse currentAccount = null;
            boolean createNewAccountClicked = false;
            boolean backStackAvailable = true;
            DataServices.Forum clickedForum = null;

        }
    }

    @Override
    public void registeredAccount(DataServices.AuthResponse account) {
        currentAccount = account;
        if(currentAccount != null){
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Forums.newInstance(currentAccount))
                    .commit();

        }
    }

    @Override
    public void cancelNewForumClicked(boolean status) {
        if(status){
                setTitle(R.string.forums);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Forums.newInstance(currentAccount))
                        .commit();
        }
    }

    @Override
    public void newForum(DataServices.Forum forum) {
        if(forum != null){
            setTitle(R.string.forum);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Forum.newInstance(forum,currentAccount))
                    .addToBackStack(null)
                    .commit();
        }
    }
}