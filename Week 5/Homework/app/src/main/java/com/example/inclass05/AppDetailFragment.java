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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppDetailFragment extends Fragment {


    private static final String SELECTED_APP = "Selected App";

    // TODO: Rename and change types of parameters
    private DataServices.App application;

    public AppDetailFragment() {
        // Required empty public constructor
    }

    public static AppDetailFragment newInstance(DataServices.App app) {
        AppDetailFragment fragment = new AppDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(SELECTED_APP, app );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            application = (DataServices.App)getArguments().getSerializable(SELECTED_APP);
        }
        getActivity().setTitle(getResources().getString(R.string.appDetails));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_app_detail, container, false);

        TextView appName = view.findViewById(R.id.appNameAppDetailFragment);
        TextView artistName = view.findViewById(R.id.artistNameAppDetailFragment);
        TextView releaseDate = view.findViewById(R.id.releaseDataAppDetailFragment);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGenresAppDetailFragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SingleRowRecyclerGenreViewAdapter adapter = new SingleRowRecyclerGenreViewAdapter(application.genres);
        recyclerView.setAdapter(adapter);
        appName.setText(application.name);
        artistName.setText(application.artistName);
        releaseDate.setText(application.releaseDate);
        getActivity().setTitle(getResources().getString(R.string.appDetails));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivity().setTitle(getResources().getString(R.string.appDetails));
    }
}