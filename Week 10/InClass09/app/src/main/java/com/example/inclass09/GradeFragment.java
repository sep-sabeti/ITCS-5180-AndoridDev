package com.example.inclass09;

import android.content.Context;
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

import java.util.ArrayList;


public class GradeFragment extends Fragment implements GradCardHolderAdapter.ICardHolderListener {


    ArrayList<Grade> grades = new ArrayList<>();
    DatabaseManager dm;
    IGradeListListeer mListener;

    public GradeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dm = new DatabaseManager(getContext());

        TextView gpa , hours;
        RecyclerView recyclerView;

        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        gpa = view.findViewById(R.id.gpa);
        hours = view.findViewById(R.id.hours);
        recyclerView = view.findViewById(R.id.recyclerView);

        Button addCourse = view.findViewById(R.id.addCourse);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addCourseClicked(true);
            }
        });

        getActivity().setTitle(R.string.courses);

        gpa.setText(getResources().getString(R.string.GPA) + " : " + dm.getDAO().getGPA());
        hours.setText(getResources().getString(R.string.hours )+ " : " + dm.getDAO().getHours());

        GradeFragment fragment = this;
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        GradCardHolderAdapter adapter = new GradCardHolderAdapter(dm.getDAO().getAllGrades() , fragment , getContext() );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IGradeListListeer){
            mListener = (IGradeListListeer)context;
        }
    }

    @Override
    public void deleteClicked(boolean status) {
        if(status){

            mListener.deleteClicked(status);

        }
    }

    public interface IGradeListListeer{
        void addCourseClicked(boolean status);
        void deleteClicked(boolean status);
    }
}