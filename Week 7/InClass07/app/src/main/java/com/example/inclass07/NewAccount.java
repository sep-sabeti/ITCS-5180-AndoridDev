package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;
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

public class NewAccount extends Fragment {

    ICreateNewContactListener mListener;
    FormBody formBody;
    TextView name,email,phone,base;

    OkHttpClient client = new OkHttpClient();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_account, container, false);
        Button submit;
        getActivity().setTitle(R.string.newContact);

        name = view.findViewById(R.id.nameForm);
        email = view.findViewById(R.id.emailForm);
        phone = view.findViewById(R.id.numberForm);
        base = view.findViewById(R.id.numberBaseForm);

        submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("")){
                    if(!email.getText().toString().equals("")){
                        if(isEmailValid(email.getText().toString())){
                            if(!phone.getText().toString().equals("")){
                                if(!base.getText().toString().equals("")){
                                    formBody = new FormBody.Builder()
                                            .add("name",name.getText().toString())
                                            .add("email",email.getText().toString())
                                            .add("phone",phone.getText().toString())
                                            .add("type",base.getText().toString())
                                            .build();
                                    try{
                                        HttpUrl.Builder builder = new HttpUrl.Builder();
                                        HttpUrl url = builder.scheme("https")
                                                .host("www.theappsdr.com")
                                                .addPathSegment("contact")
                                                .addPathSegment("create")
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
                                                            Toast.makeText(getContext(),getResources().getString(R.string.Toast_successfulCreate),Toast.LENGTH_SHORT).show();
                                                            mListener.newCratedAccountStatus(true);
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
        if(context instanceof ICreateNewContactListener){
            mListener = (ICreateNewContactListener)context;
        }
    }

    public interface ICreateNewContactListener{
        void newCratedAccountStatus(Boolean status);
    }











}