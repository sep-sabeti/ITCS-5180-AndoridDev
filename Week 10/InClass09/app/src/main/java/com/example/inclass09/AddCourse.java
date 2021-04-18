package com.example.inclass09;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class AddCourse extends Fragment {

    IAddACourseListener mListener;
    DatabaseManager dm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final TextView name , credit , number ;
        Button submit , cancel;
        final RadioGroup radioGroup;
        final View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        submit = view.findViewById(R.id.submit);
        name = view.findViewById(R.id.courseName);
        number = view.findViewById(R.id.courseNumber);
        credit = view.findViewById(R.id.creditHours);
        final String[] courseGrade = {""};
        cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelClicked(true);
            }
        });

        radioGroup = view.findViewById(R.id.radioGroup);

        getActivity().setTitle(R.string.addCourse);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTo = name.getText().toString();
                String numberTo = number.getText().toString();

                try {
                    int creditTo = Integer.parseInt(credit.getText().toString());

                    if(creditTo > 0){
                        if(!nameTo.equals("")){
                            if(!numberTo.equals("")){


                                if(radioGroup.getCheckedRadioButtonId() != -1){


                                    if(radioGroup.getCheckedRadioButtonId() == view.findViewById(R.id.a).getId()){
                                        dm = new DatabaseManager(getContext());
                                        Grade course = new Grade(numberTo,nameTo, getResources().getString(R.string.a),creditTo);
                                        dm.getDAO().insert(course);
                                        mListener.submitClicked(true);
                                    } else                                 if(radioGroup.getCheckedRadioButtonId() == view.findViewById(R.id.b).getId()){
                                        dm = new DatabaseManager(getContext());
                                        Grade course = new Grade(numberTo,nameTo, getResources().getString(R.string.b),creditTo);
                                        dm.getDAO().insert(course);
                                        mListener.submitClicked(true);
                                    }
                                    else                                 if(radioGroup.getCheckedRadioButtonId() == view.findViewById(R.id.c).getId()){
                                        dm = new DatabaseManager(getContext());
                                        Grade course = new Grade(numberTo,nameTo, getResources().getString(R.string.c),creditTo);
                                        dm.getDAO().insert(course);
                                        mListener.submitClicked(true);
                                    }
                                    else                                 if(radioGroup.getCheckedRadioButtonId() == view.findViewById(R.id.d).getId()){
                                        dm = new DatabaseManager(getContext());
                                        Grade course = new Grade(numberTo,nameTo, getResources().getString(R.string.d),creditTo);
                                        dm.getDAO().insert(course);
                                        mListener.submitClicked(true);
                                    }
                                    else                                 if(radioGroup.getCheckedRadioButtonId() == view.findViewById(R.id.f).getId()){
                                        dm = new DatabaseManager(getContext());
                                        Grade course = new Grade(numberTo,nameTo, getResources().getString(R.string.f),creditTo);
                                        dm.getDAO().insert(course);
                                        mListener.submitClicked(true);
                                    }

                                } else {
                                    Toast.makeText(getContext(), getResources().getString(R.string.toast_grade), Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(getContext(), getResources().getString(R.string.toast_input), Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.toast_input), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.toast_credit), Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e){
                    Toast.makeText(getContext(), getResources().getString(R.string.toast_credit), Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IAddACourseListener){
            mListener = (IAddACourseListener)context;
        }
    }

    public interface IAddACourseListener{
        void submitClicked(boolean status);
        void cancelClicked(boolean status);
    }
}