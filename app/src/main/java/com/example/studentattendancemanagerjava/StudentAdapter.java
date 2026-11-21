package com.example.studentattendancemanagerjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final ArrayList<Student> students;

    public StudentAdapter(ArrayList<Student> students) {
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
        Student student = students.get(position);

        holder.txtStudentName.setText(student.getName());

        updateStatusText(holder, student);

        holder.btnPresent.setOnClickListener(v -> {
            student.setStatus(Student.Status.PRESENT);
            updateStatusText(holder, student);
        });

        holder.btnAbsent.setOnClickListener(v -> {
            student.setStatus(Student.Status.ABSENT);
            updateStatusText(holder, student);
        });
    }

    public void updateStatusText(StudentViewHolder holder, Student student) {

        switch (student.getStatus()) {
            case PRESENT:
                holder.txtStatus.setText("Present");
                holder.txtStatus.setTextColor(0xFF2E7D32);
                break;
            
            case ABSENT:
                holder.txtStatus.setText("Absent");
                holder.txtStatus.setTextColor(0xFFC62828);
                break;

            default:
                holder.txtStatus.setText("Not Marked");
                holder.txtStatus.setTextColor(0xFF555555);
                break;
        }
    }



    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView txtStudentName;
        TextView txtStatus;
        Button btnPresent;
        Button btnAbsent;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStudentName = itemView.findViewById(R.id.txtStudentName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnPresent = itemView.findViewById(R.id.btnPresent);
            btnAbsent = itemView.findViewById(R.id.btnAbsent);
        }

    }

    
}
