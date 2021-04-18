package com.example.inclass09;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradCardHolderAdapter extends RecyclerView.Adapter<GradCardHolderAdapter.CardHolder> {

    ArrayList<Grade> grades;
    GradeFragment fragment;
    ICardHolderListener mListener;
    Context context;
    public GradCardHolderAdapter(ArrayList<Grade>grades , GradeFragment fragment , Context context){
        this.grades = grades;
        this.fragment = fragment;
        this.context = context;

        mListener = fragment;
    }


    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_card_view , parent,false);
        CardHolder cardHolder = new CardHolder(view);
        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardHolder holder, final int position) {

        holder.grade.setText(grades.get(position).getCourseGrade());
        holder.name.setText(grades.get(position).getCourseName());
        holder.number.setText(grades.get(position).getCourseNumber());
        holder.creditHour.setText(grades.get(position).getCourseCreditHours()+"");

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseManager dm = new DatabaseManager(context);
                dm.getDAO().delete(grades.get(position));
                mListener.deleteClicked(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.grades.size();
    }

    public static class CardHolder extends RecyclerView.ViewHolder{

        TextView grade, name, number , creditHour;
        ImageView delete;
        public CardHolder(@NonNull View itemView) {
            super(itemView);

            grade = itemView.findViewById(R.id.courseGrade);
            name = itemView.findViewById(R.id.courseName);
            number = itemView.findViewById(R.id.courseTitle);
            creditHour = itemView.findViewById(R.id.courseCreditHour);
            delete = itemView.findViewById(R.id.imageView);
        }
    }



    public interface ICardHolderListener {
        void deleteClicked(boolean status);
    }
}
