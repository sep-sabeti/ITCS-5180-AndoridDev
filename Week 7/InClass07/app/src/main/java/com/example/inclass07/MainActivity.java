package com.example.inclass07;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.HttpUrl;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactList.IContactListListener, NewAccount.ICreateNewContactListener, ContactDetail.IContactDetailListener, ContactUpdate.IUpdateAccountListner {
    final public static String TAG = "InClass07";
    private OkHttpClient client = new OkHttpClient();

    public ArrayList<Contact> contactLists = new ArrayList<Contact>();
    //public booleans for controlling the flow of the app;
    public boolean addNewContactClicked = false;
    public String deleteButtonFromContactListClickedId = null;
    public String contactClickedFromContactList  = null;
    public boolean createdNewAccountStatus = false;
    public boolean updateContactClicked = false;
    public Contact toBeUpdatedContact = null;
    public boolean updateAccountStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this,getResources().getString(R.string.Toast_welcome),Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new ContactList())
                .commit();
    }

    @Override
    public void newContactClicked(boolean status) {
        addNewContactClicked = status;
        if(addNewContactClicked){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new NewAccount())
                    .addToBackStack(null)
                    .commit();
        }

    }

    @Override
    public void deleteButtonFromListContactId(String id) {
        deleteButtonFromContactListClickedId = id;
        if(deleteButtonFromContactListClickedId != null && !deleteButtonFromContactListClickedId.equals("")){

            FormBody formBody = new FormBody.Builder()
                    .add("id",deleteButtonFromContactListClickedId)
                    .build();

            HttpUrl.Builder builder = new HttpUrl.Builder();
            HttpUrl url = builder.scheme("https")
                    .host("www.theappsdr.com")
                    .addPathSegment("contact")
                    .addPathSegment("delete")
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if(response.isSuccessful()){
                        ResponseBody contacts = response.body();
                        String body = contacts.string();
                        Log.d(TAG, "onResponse" + body);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_successfulDelete), Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container,new ContactList())
                                        .commit();
                            }
                        });
                    }
                }
            });

        }
    }

    @Override
    public void contactClicked(String id) {
        contactClickedFromContactList = id;
        if(contactClickedFromContactList != null && !contactClickedFromContactList.equals("")){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,ContactDetail.newInstance(contactClickedFromContactList))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void newCratedAccountStatus(Boolean status) {
        createdNewAccountStatus = status;
        if(createdNewAccountStatus){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new ContactList())
                    .commit();
        }
    }

    @Override
    public void updateContactClicked(boolean status) {
        updateContactClicked = status;
        if(updateContactClicked){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,ContactUpdate.newInstance(toBeUpdatedContact))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void updateContact(Contact contact) {
        toBeUpdatedContact = contact;
    }

    @Override
    public void updateAccountStatus(boolean status) {
        updateAccountStatus = status;
        if(updateAccountStatus){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,new ContactList())
                    .commit();
        }
    }
}