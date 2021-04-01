package com.example.inclass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements Login.ILoginListener, CreateNewAccount.ICreateNewAccountListener, Forums.IForumsListner , NewForum.INewForumListner {

    public String TAG = "App";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(FirebaseAuth.getInstance().getUid() ==  null || FirebaseAuth.getInstance().getCurrentUser() == null){
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new Login())
                    .commit();
        } else {
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Forums())
                    .commit();
        }
    }

    @Override
    public void loginClicked(boolean status) {
        if(status){
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Forums())
                    .commit();
        }

    }

    @Override
    public void createNewAccountClicked(boolean status) {
        if(status){
            setTitle(R.string.create);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new CreateNewAccount())
                    .commit();
        }

    }

    @Override
    public void createdAccountStatus(boolean status) {
        if(status){
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Forums())
                    .addToBackStack(null)
                    .commit();

        }

    }



    @Override
    public void cancelCreatedClicked(boolean status) {
        if(status){
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Login())
                    .commit();
        }

    }

    @Override
    public void logOutClicked(boolean status) {
        if(status){
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Login())
                    .commit();
        }
    }

    @Override
    public void newForomClicked(boolean status) {
        if(status){
            setTitle(R.string.newForum);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new NewForum())
                    .commit();
        }
    }

    @Override
    public void newForumSubmit(boolean status) {
        if(status){
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new Forums())
                    .commit();
        }

    }

    @Override
    public void cancelForumClicked(boolean status) {
        if(status){
            if(status){
                setTitle(R.string.forums);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new Forums())
                        .commit();
            }
        }
    }
}