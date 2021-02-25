package com.example.inclass05;

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

import java.util.ArrayList;


public class TopPaidAppsFragment extends Fragment implements RecyclerViewAdapter.ISelectedAppInRecyclerView {


    private static final String CALLBACK = "callback";
    private static final String APP_CATEGORY = "App category";
    private static final String KEY_ACCOUNT = "Account key";

    private Callback callbackFragment;
    private String appCategoryFragment;
    private String accountKeyFragment;

    ISelectedAppListener mSelectedAppListener;
    DataServices.App selectedAppFromRecyclerView;

    public TopPaidAppsFragment() {
        // Required empty public constructor
    }


    public static TopPaidAppsFragment newInstance(Callback callback, String appCategory,String keyAccount) {
        TopPaidAppsFragment fragment = new TopPaidAppsFragment();
        Bundle args = new Bundle();
        args.putSerializable(CALLBACK, callback);
        args.putString(APP_CATEGORY, appCategory);
        args.putString(KEY_ACCOUNT, keyAccount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            callbackFragment = (Callback)getArguments().getSerializable(CALLBACK);
            appCategoryFragment = getArguments().getString(APP_CATEGORY);
            accountKeyFragment = getArguments().getString(KEY_ACCOUNT);
        }
        getActivity().setTitle(getResources().getString(R.string.topPaidApps));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_top_paid_apps, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTopPaidAppsFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DataServices.getAppsByCategory(accountKeyFragment,appCategoryFragment,callbackFragment.new DataResponse());
        final ArrayList<Object> appData = callbackFragment.getData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(appData , this);
        recyclerView.setAdapter(adapter);
        getActivity().setTitle(getResources().getString(R.string.topPaidApps));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ISelectedAppListener){
            mSelectedAppListener = (ISelectedAppListener)context;
        }
        getActivity().setTitle(getResources().getString(R.string.topPaidApps));

    }

    @Override
    public void selectedAppInRecyclerView(DataServices.App app) {
        selectedAppFromRecyclerView = app;
        mSelectedAppListener.selectedApp(app);
    }

    public interface ISelectedAppListener {
        void selectedApp(DataServices.App app);
    }
}