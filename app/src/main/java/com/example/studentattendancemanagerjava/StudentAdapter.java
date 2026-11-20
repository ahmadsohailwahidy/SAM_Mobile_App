package com.example.studentattendancemanagerjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final ArrayList<String> students;

    public StudentAdapter(ArrayList<String> students) {
        this.students = students;


    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent, int ViewType
    ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
        @NonNull StudentViewHolder holder, int position
    ) {
        String studentName = students.get(position);

        holder.txtStudentName.setText(studentName);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView txtStudentName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStudentName = itemView.findViewById(R.id.txtStudentName);
        }

    }

    
}
