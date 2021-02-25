package com.example.inclass05;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class AppCategoriesFragment extends Fragment implements SingleRowRecyclerViewAdapter.IAppCategoryClickedInRecyclerView {

    private static final String ACCOUNT = "account";
    private static final String ACCOUNT_KEY = "account key";
    private static final String CALLBACK = "Callback";

    private DataServices.Account accountFragment;
    private String accountKeyFragment;
    private Callback callbackFragment;

    String selectedAccountFromRecyclerView;

    IAppCategoryClickingListener mAppCategoryClickingListener;
    public AppCategoriesFragment() {
        // Required empty public constructor
    }

    public static AppCategoriesFragment newInstance(DataServices.Account account , String accountKey , Callback callback) {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ACCOUNT, account);
        args.putSerializable(CALLBACK, callback);
        args.putString(ACCOUNT_KEY,accountKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.accountFragment = (DataServices.Account) getArguments().getSerializable(ACCOUNT);
            this.accountKeyFragment = getArguments().getString(ACCOUNT_KEY);
            this.callbackFragment = (Callback) getArguments().getSerializable(CALLBACK);
        }
        getActivity().setTitle(getResources().getString(R.string.appCategories));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);


        DataServices.getAppCategories(accountKeyFragment,callbackFragment.new DataResponse());
        final ArrayList<Object> appCategories = callbackFragment.getData();
        TextView welcome = view.findViewById(R.id.welcomeAppCategoriesFragment);
        welcome.setText(getResources().getString(R.string.welcome) + " " + accountFragment.getName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAppCategories);
        recyclerView.setLayoutManager(layoutManager);

        SingleRowRecyclerViewAdapter adapter = new SingleRowRecyclerViewAdapter(appCategories , this );
        recyclerView.setAdapter(adapter);





        Button logOut = view.findViewById(R.id.logoutAppCategoriesFragment);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.confirmation))
                        .setMessage(getResources().getString(R.string.confirmationMessage))
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAppCategoryClickingListener.logOutClicked(true);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAppCategoryClickingListener.logOutClicked(false);
                            }
                        }).create().show();
            }
        });
        getActivity().setTitle(getResources().getString(R.string.appCategories));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IAppCategoryClickingListener){
            mAppCategoryClickingListener = (IAppCategoryClickingListener) context;
            getActivity().setTitle(getResources().getString(R.string.appCategories));
        }
    }

    @Override
    public void selectedAppCategory(String appCategory) {
        selectedAccountFromRecyclerView = appCategory;
        mAppCategoryClickingListener.clickedAppCategory(appCategory);

    }

    public interface IAppCategoryClickingListener {
        void clickedAppCategory(String appCategory);
        void logOutClicked(boolean status);
    }
}