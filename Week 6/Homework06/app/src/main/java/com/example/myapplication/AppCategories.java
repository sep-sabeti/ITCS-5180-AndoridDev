package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AppCategories extends Fragment implements CardRecyclerViewAdapter.IItemClickedListener {


    private static final String ACCOUNT = "currentAccount";
    private ArrayList<String> appCategoriesList;
    private DataServices.Account currentAccount;
    public IAppCategoriesListener mAppCategoriesListner;
    private String account;
    public String clickedAppCategory = null;
    public CardRecyclerViewAdapter adapter;
    public CardRecyclerViewAdapter.IItemClickedListener iItemClickedListener;
    public AppCategories() {
    }


    // TODO: Rename and change types and number of parameters
    public static AppCategories newInstance(String account) {
        AppCategories fragment = new AppCategories();
        Bundle args = new Bundle();
        args.putString(ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.appCategories);

        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);
        try{

            Button logout = view.findViewById(R.id.logoutButton);
            logout.setEnabled(false);
            iItemClickedListener = this;

            TextView welcome = view.findViewById(R.id.welcomeTextView);
            welcome.setText(getResources().getString(R.string.pleaseWait));
            ArrayList<String> appCategoriesList = new ArrayList<String>();
            RecyclerView recyclerView = view.findViewById(R.id.appCategoriesRecyclerView);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new CardRecyclerViewAdapter(appCategoriesList, this);
            recyclerView.setAdapter(adapter);

            GetAccountAsync getAccountAsync = new GetAccountAsync();
            getAccountAsync.execute(account);
            GetAppCategoriesListAsync getAppCategoriesListAsync = new GetAppCategoriesListAsync();
            getAppCategoriesListAsync.execute(account);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getResources().getString(R.string.confrimation))
                            .setMessage(getResources().getString(R.string.confrimation_Message2))
                            .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mAppCategoriesListner.logOutClicked(true);
                                    Toast.makeText(getContext(), getResources().getString(R.string.Toast_logout), Toast.LENGTH_SHORT).show();
                                }
                            }).create().show();
                }
            });
        } catch (Exception e){
            mAppCategoriesListner.someThingWentWrong(true);
        }

        return view;
    }



    @Override
    public void clickedAppCategory(String selectedAppCategory) {
        clickedAppCategory = selectedAppCategory;
        mAppCategoriesListner.mAppCategoriesListener(clickedAppCategory);
    }


    public class GetAccountAsync extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_wait), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView welcome = getView().findViewById(R.id.welcomeTextView);
            welcome.setText(getResources().getString(R.string.welcome) + " " + currentAccount.getName());
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                DataServices.Account account = DataServices.getAccount(strings[0]);
                currentAccount = account;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public class GetAppCategoriesListAsync extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_wait), Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                getView().findViewById(R.id.logoutButton).setEnabled(true);
                Button logout = getView().findViewById(R.id.logoutButton);
                RecyclerView recyclerView = getView().findViewById(R.id.appCategoriesRecyclerView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new CardRecyclerViewAdapter(appCategoriesList, iItemClickedListener);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getContext(), getResources().getString(R.string.Toast_done), Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(getContext(), getResources().getString(R.string.Toast_wrong), Toast.LENGTH_SHORT).show();
                mAppCategoriesListner.someThingWentWrong(true);
            }

        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                ArrayList<String> appCategories = DataServices.getAppCategories(strings[0]);
                appCategoriesList = appCategories;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IAppCategoriesListener){
            mAppCategoriesListner = (IAppCategoriesListener)(context);
        }
    }

    public interface IAppCategoriesListener{
        void logOutClicked(boolean status);
        void mAppCategoriesListener(String appCategory);
        void someThingWentWrong(boolean status);
    }


}