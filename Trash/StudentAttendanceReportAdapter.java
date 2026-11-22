

package com.example.studentattendancemanagerjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class StudentAttendanceReportAdapter extends
        RecyclerView.Adapter<StudentAttendanceReportAdapter.ReportViewHolder> {

    private final ArrayList<StudentAttendanceReport> reports;

    public StudentAttendanceReportAdapter(
            ArrayList<StudentAttendanceReport> reports) {

        this.reports = reports;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_report, parent, false);

        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ReportViewHolder holder, int position) {

        StudentAttendanceReport report = reports.get(position);

        holder.txtName.setText(report.getStudentName());

        holder.txtPresent.setText(
                "Present: " + report.getPresentCount()
        );

        holder.txtAbsent.setText(
                "Absent: " + report.getAbsentCount()
        );

        holder.txtTotal.setText(
                "Total Marked: " + report.getTotalMarked()
        );

        if (report.getTotalMarked() == 0) {

            holder.txtRate.setText("Attendance Rate: N/A");

        } else {

            String rate = String.format(
                    Locale.getDefault(),
                    "%.1f%%",
                    report.getAttendanceRate()
            );

            holder.txtRate.setText(
                    "Attendance Rate: " + rate
            );
        }
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtPresent;
        TextView txtAbsent;
        TextView txtTotal;
        TextView txtRate;

        public ReportViewHolder(@NonNull View itemView) {

            super(itemView);

            txtName = itemView.findViewById(
                    R.id.txtReportStudentName
            );

            txtPresent = itemView.findViewById(
                    R.id.txtReportPresent
            );

            txtAbsent = itemView.findViewById(
                    R.id.txtReportAbsent
            );

            txtTotal = itemView.findViewById(
                    R.id.txtReportTotal
            );

            txtRate = itemView.findViewById(
                    R.id.txtReportRate
            );
        }
    }
}
