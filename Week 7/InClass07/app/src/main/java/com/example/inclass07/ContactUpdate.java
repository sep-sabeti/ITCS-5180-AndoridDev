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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class ContactUpdate extends Fragment {

    private static final String CONTACT = "contact";
    FormBody formBody;
    TextView nameUpdate,emailUpdate,phoneUpdate,baseUpdate;
    OkHttpClient client = new OkHttpClient();
    IUpdateAccountListner mListener;



    private Contact contact;

    public ContactUpdate() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ContactUpdate newInstance(Contact contact) {
        ContactUpdate fragment = new ContactUpdate();
        Bundle args = new Bundle();
        args.putSerializable(CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = (Contact) getArguments().getSerializable(CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_update, container, false);

        Button update;
        getActivity().setTitle(R.string.updateContact);

        nameUpdate = view.findViewById(R.id.nameFormUpdate);
        emailUpdate = view.findViewById(R.id.emailFormUpdate);
        phoneUpdate = view.findViewById(R.id.numberFormUpdate);
        baseUpdate = view.findViewById(R.id.numberBaseFormUpdate);

        update = view.findViewById(R.id.submitButtonUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nameUpdate.getText().toString().equals("")){
                    if(!emailUpdate.getText().toString().equals("")){
                        if(isEmailValid(emailUpdate.getText().toString())){
                            if(!phoneUpdate.getText().toString().equals("")){
                                if(!baseUpdate.getText().toString().equals("")){
                                    formBody = new FormBody.Builder()
                                            .add("id",contact.id)
                                            .add("name",nameUpdate.getText().toString())
                                            .add("email",emailUpdate.getText().toString())
                                            .add("phone",phoneUpdate.getText().toString())
                                            .add("type",baseUpdate.getText().toString())
                                            .build();
                                    try{
                                        HttpUrl.Builder builder = new HttpUrl.Builder();
                                        HttpUrl url = builder.scheme("https")
                                                .host("www.theappsdr.com")
                                                .addPathSegment("contact")
                                                .addPathSegment("update")
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
                                                    ResponseBody responseBody = response.body();
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(getContext(),getResources().getString(R.string.Toast_successfulUpdate),Toast.LENGTH_SHORT).show();
                                                            mListener.updateAccountStatus(true);
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    } catch (Exception e){

                                    }
                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingType), Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingNumber), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.Toast_wrongEmail), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingEmail), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.Toast_missingName), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IUpdateAccountListner){
            mListener = (IUpdateAccountListner)context;
        }
    }

    public interface IUpdateAccountListner{
        void updateAccountStatus(boolean status);
    }
}