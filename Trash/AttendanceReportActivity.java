
package com.example.studentattendancemanagerjava;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AttendanceReportActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private ArrayList<StudentAttendanceReport> reports =
            new ArrayList<>();

    private StudentAttendanceReportAdapter reportAdapter;

    private TextView txtSelectedMonth;
    private TextView txtEmpty;

    private RecyclerView recyclerReports;

    private Calendar selectedMonth = Calendar.getInstance();

    private final SimpleDateFormat monthFormatter =
            new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    private final SimpleDateFormat databaseDateFormatter =
            new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_attendance_report);

        databaseHelper = new DatabaseHelper(this);

        txtSelectedMonth = findViewById(R.id.txtSelectedMonth);
        txtEmpty = findViewById(R.id.txtEmpty);

        Button btnChooseMonth = findViewById(R.id.btnChooseMonth);

        recyclerReports = findViewById(R.id.recyclerReports);

        reportAdapter = new StudentAttendanceReportAdapter(reports);

        recyclerReports.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerReports.setAdapter(reportAdapter);

        updateMonthLabel();

        loadMonthlyReport();

        btnChooseMonth.setOnClickListener(v -> showMonthPicker());
    }

    private void showMonthPicker() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,

                (view, year, month, dayOfMonth) -> {

                    selectedMonth.set(
                            year,
                            month,
                            1
                    );

                    updateMonthLabel();

                    loadMonthlyReport();
                },

                selectedMonth.get(Calendar.YEAR),
                selectedMonth.get(Calendar.MONTH),
                selectedMonth.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateMonthLabel() {

        txtSelectedMonth.setText(
                "Month: " +
                monthFormatter.format(selectedMonth.getTime())
        );
    }

    private void loadMonthlyReport() {

        Calendar startCalendar =
                (Calendar) selectedMonth.clone();

        startCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Calendar endCalendar =
                (Calendar) startCalendar.clone();

        endCalendar.add(Calendar.MONTH, 1);

        String startDate =
                databaseDateFormatter.format(
                        startCalendar.getTime()
                );

        String endDate =
                databaseDateFormatter.format(
                        endCalendar.getTime()
                );

        reports.clear();

        reports.addAll(
                databaseHelper.getMonthlyAttendanceReport(
                        startDate,
                        endDate
                )
        );

        reportAdapter.notifyDataSetChanged();

        if (reports.isEmpty()) {

            txtEmpty.setVisibility(View.VISIBLE);

            recyclerReports.setVisibility(View.GONE);

        } else {

            txtEmpty.setVisibility(View.GONE);

            recyclerReports.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {

        if (databaseHelper != null) {
            databaseHelper.close();
        }

        super.onDestroy();
    }
}
