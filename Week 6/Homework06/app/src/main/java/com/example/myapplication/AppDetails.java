package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

public class AppDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CURRENT_APP = "app";

    // TODO: Rename and change types of parameters
    private DataServices.App app;

    public AppDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AppDetails newInstance(DataServices.App app) {
        AppDetails fragment = new AppDetails();
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_APP, (Serializable) app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (DataServices.App) getArguments().getSerializable(CURRENT_APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);
        ListView listView = view.findViewById(R.id.genreListView);

        getActivity().setTitle(R.string.appDetails);

        ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,android.R.id.text1,app.genres);
        listView.setAdapter(adapter);

        TextView appName, appArtist,appReleaseDate;
        appName = view.findViewById(R.id.appNameTextView);
        appArtist = view.findViewById(R.id.artistNameTextView);
        appReleaseDate = view.findViewById(R.id.releaseDateTextView);
        appName.setText(app.name);
        appArtist.setText(app.artistName);
        appReleaseDate.setText(app.releaseDate);
        return view;
    }
}