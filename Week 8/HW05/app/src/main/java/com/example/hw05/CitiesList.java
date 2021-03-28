package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class CitiesList extends Fragment {
    ICitiesListener mListener;

    public CitiesList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities_list, container, false);
        ListView listViewCities = view.findViewById(R.id.listCities);

        ArrayList<Data.City> theseCities = Data.cities;
        ArrayAdapter<Data.City> cityAdapter = new ArrayAdapter<Data.City>(getContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                theseCities);

        getActivity().setTitle(R.string.cities_label);
        listViewCities.setAdapter(cityAdapter);

        mListener.backableCitiesList(false);

        listViewCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.clickedCity(theseCities.get(position));
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ICitiesListener){
            mListener = (ICitiesListener) context;
        }
    }

    public interface ICitiesListener{
        void clickedCity(Data.City city);
        void backableCitiesList(boolean status);
    }
}