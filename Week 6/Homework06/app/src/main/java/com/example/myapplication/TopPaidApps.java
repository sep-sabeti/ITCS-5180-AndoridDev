package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class TopPaidApps extends Fragment implements CardViewHolderAdapter.ICardViewAppListner {


    private static final String ACCOUNT = "currentAccount";
    private static final String APP_Category = "appCategory";
    private ArrayList<DataServices.App> applications = new ArrayList<DataServices.App>();
    private CardViewHolderAdapter adapter;
    public CardViewHolderAdapter.ICardViewAppListner mTopPaidApssListener;
    public DataServices.App selectedApp = null;
    ITopPaidAppsListener mTopPaidAppsListener;
    private String account;
    private String appCategory;
    public boolean goToDetailAppStatus = false;

    public TopPaidApps() {
    }


    // TODO: Rename and change types and number of parameters
    public static TopPaidApps newInstance(String account , String appCategory) {
        TopPaidApps fragment = new TopPaidApps();
        Bundle args = new Bundle();
        args.putString(ACCOUNT, account);
        args.putString(APP_Category, appCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString(ACCOUNT);
            appCategory = getArguments().getString(APP_Category);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_paid_apps, container, false);

        try{
            getActivity().setTitle(R.string.topPaidApps);
            mTopPaidApssListener = (CardViewHolderAdapter.ICardViewAppListner) this;
            RecyclerView recyclerView = view.findViewById(R.id.topPaidAppsRecyclerView);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            applications = new ArrayList<DataServices.App>();
            adapter = new CardViewHolderAdapter(applications , mTopPaidApssListener);
            recyclerView.setAdapter(adapter);

            GetAppByCategory getAppByCategory = new GetAppByCategory();
            getAppByCategory.execute(account,appCategory);

        } catch (Exception e){
            mTopPaidAppsListener.wentWrong(true);
        }
        return view;

    }

    @Override
    public void clickedApp(DataServices.App app) {
        selectedApp = app;
        mTopPaidAppsListener.selectedApp(selectedApp);
    }

    @Override
    public void goToAppStatus(boolean status) {
        goToDetailAppStatus = status;
        mTopPaidAppsListener.goToAppDetailStatus(status);
    }


    public class GetAppByCategory extends AsyncTask<String,Void,ArrayList<DataServices.App>>{

        @Override
        protected void onPostExecute(ArrayList<DataServices.App> apps) {
            super.onPostExecute(apps);
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_done), Toast.LENGTH_SHORT).show();
            RecyclerView recyclerView = getView().findViewById(R.id.topPaidAppsRecyclerView);
            applications = apps;
            adapter = new CardViewHolderAdapter(applications , mTopPaidApssListener);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), getResources().getString(R.string.Toast_wait), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<DataServices.App> doInBackground(String... strings) {
            ArrayList<DataServices.App> results = null;
            try {
                ArrayList<DataServices.App> apps = DataServices.getAppsByCategory(strings[0],strings[1]);
                results = apps;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return  results;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ITopPaidAppsListener){
            mTopPaidAppsListener = (ITopPaidAppsListener)context;
        }
    }

    public interface ITopPaidAppsListener{
        void selectedApp(DataServices.App app);
        void goToAppDetailStatus(boolean status);
        void wentWrong(boolean status);
    }
}