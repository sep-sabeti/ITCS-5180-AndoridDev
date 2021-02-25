package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        ListView listView = view.findViewById(R.id.listViewGenresAppDetailFragment);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,application.genres);
        listView.setAdapter(arrayAdapter);

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