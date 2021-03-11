package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ContactDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONTACT = "contact";
    OkHttpClient client = new OkHttpClient();
    IContactDetailListener mListener;

    // TODO: Rename and change types of parameters
    private String contactID;

    public ContactDetail() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ContactDetail newInstance(String contactId) {
        ContactDetail fragment = new ContactDetail();
        Bundle args = new Bundle();
        args.putString(CONTACT, contactId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactID =  getArguments().getString(CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

     View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
     getActivity().setTitle(getResources().getString(R.string.contactDetail));

        Button update;

        update = view.findViewById(R.id.updateButton);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.updateContactClicked(true);
            }
        });


        HttpUrl.Builder builder = new HttpUrl.Builder();
        HttpUrl url = builder.scheme("https")
                .host("www.theappsdr.com")
                .addPathSegment("contact")
                .addPathSegment(contactID)
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
                    ResponseBody responseBody = response.body();
                    String contact = responseBody.string();
                    final Contact contactDetail = new Contact(contact.split(",")[0],contact.split(",")[1],contact.split(",")[2],contact.split(",")[3],contact.split(",")[4]);
                   getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView name , email , number, numberBase;
                            name = getView().findViewById(R.id.name);
                            name.setText(contactDetail.name);
                            email = getView().findViewById(R.id.email);
                            email.setText(contactDetail.email);
                            number = getView().findViewById(R.id.number);
                            number.setText(contactDetail.number);
                            numberBase = getView().findViewById(R.id.numberBase);
                            numberBase.setText(contactDetail.numberBase);
                            mListener.updateContact(contactDetail);

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
        if(context instanceof IContactDetailListener){
            mListener = (IContactDetailListener)context;
        }
    }

    public interface IContactDetailListener{
        void updateContactClicked(boolean status);
        void updateContact(Contact contact);
    }

}