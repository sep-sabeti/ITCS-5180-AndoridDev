package com.example.inclass08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements Login.ILoginListener, CreateNewAccount.ICreateNewAccountListener, Forums.IForumsListner , NewForum.INewForumListner {

    public static String TAG = "App";
    public CurrentUser user = null;

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
            FirebaseAuth.getInstance().signOut();
            setTitle(R.string.login);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new Login())
                    .commit();
        }
    }

    @Override
    public void loginClicked(CurrentUser user) {
        if(user != null){
            this.user = user;
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Forums.newInstance(user))
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
    public void createdAccountStatus(CurrentUser user) {
        this.user = user;
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Forums.newInstance(user))
                    .commit();

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
            this.user = null;
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
    public void forumClicked(Forum forum) {
        if(forum != null){
            setTitle(getResources().getString(R.string.forum));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ForumDescription.newInstance(forum , this.user))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void newForumSubmit(boolean status) {
        if(status){
            setTitle(R.string.forums);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Forums.newInstance(user))
                    .commit();
        }

    }

    @Override
    public void cancelForumClicked(boolean status) {
        if(status){
            if(status){
                setTitle(R.string.forums);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, Forums.newInstance(user))
                        .commit();
            }
        }
    }
}