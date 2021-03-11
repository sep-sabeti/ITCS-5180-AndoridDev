package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class ContactList extends Fragment implements ContactListRecyclerView.IContactListRecyclerViewHolderListener {

    public ContactListRecyclerView.IContactListRecyclerViewHolderListener mListener;
    public ArrayList<Contact> contactLists = new ArrayList<>();
    String deleteContactFromListViewId;
    String contactClickedId;
    IContactListListener mContactListListener;
    private static final String CONTACTS = "contacts";
    OkHttpClient client = new OkHttpClient();

    public ContactList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        getActivity().setTitle(getResources().getString(R.string.contactList));
        RecyclerView contactList = view.findViewById(R.id.contactListView);
        contactList.setHasFixedSize(true);
        mListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactList.setLayoutManager(layoutManager);
        ContactListRecyclerView adapter = new ContactListRecyclerView(contactLists , mListener);
        contactList.setAdapter(adapter);
        Button addNewContact = view.findViewById(R.id.addNewContactButton);
        addNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactListListener.newContactClicked(true);
            }
        });


        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("contacts")
                .build();

        Request request = new Request.Builder()
                .url(url)
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
                    String[] contactsDetails = body.split("\n");
                    for (String string: contactsDetails
                    ) {
                        try{
                            String[] details = string.split(",");
                            contactLists.add(new Contact(details[0],details[1],details[2],details[3],details[4]));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RecyclerView contactList = getView().findViewById(R.id.contactListView);
                            ContactListRecyclerView adapter = new ContactListRecyclerView(contactLists , mListener);
                            contactList.setAdapter(adapter);
                        }
                    });

                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IContactListListener){
            mContactListListener = (IContactListListener)context;
        }
    }

    @Override
    public void deleteButtonClicked(String id) {
        deleteContactFromListViewId = id;
        if(deleteContactFromListViewId!= null && !deleteContactFromListViewId.equals("")){
            mContactListListener.deleteButtonFromListContactId(deleteContactFromListViewId);
        }
    }

    @Override
    public void itemClicked(String id) {
        contactClickedId = id;
        if(contactClickedId != null && !contactClickedId.equals("")){
            mContactListListener.contactClicked(contactClickedId);
        }
    }

    public interface IContactListListener {
        void newContactClicked(boolean status);
        void deleteButtonFromListContactId(String id);
        void contactClicked(String id);
    }



















}






















