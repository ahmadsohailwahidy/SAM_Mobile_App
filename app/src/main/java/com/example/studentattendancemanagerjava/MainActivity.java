
package com.example.studentattendancemanagerjava;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Student> students = new ArrayList<>();

    private StudentAdapter studentAdapter;
    private DatabaseHelper databaseHelper;

    // Selected attendance date
    private String selectedDate;

    // UI Components
    private TextView txtSelectedDate;
    private TextView txtPresentCount;
    private TextView txtAbsentCount;
    private TextView txtNotMarkedCount;
    private TextView txtAttendanceRate;

    // Date Formats
    private static final String DB_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        // Initialize Database
        databaseHelper = new DatabaseHelper(this);

        // Load Students
        students.clear();

        students.addAll(databaseHelper.getAllStudents());


        // Initialize UI Components

        EditText studentName = findViewById(R.id.editStudent);

        Button addButton = findViewById(R.id.btnWelcome);

        Button selectDateButton = findViewById(R.id.btnSelectDate);

        RecyclerView recyclerView = findViewById(R.id.recyclerStudent);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);

        txtPresentCount = findViewById(R.id.txtPresentCount);

        txtAbsentCount = findViewById(R.id.txtAbsentCount);

        txtNotMarkedCount = findViewById(R.id.txtNotMarkedCount);

        txtAttendanceRate = findViewById(R.id.txtAttendanceRate);


        // Initialize Selected Date (Today)

        Date today = new Date();

        selectedDate = formatDate(today, DB_DATE_FORMAT);

        txtSelectedDate.setText(
                "Date: " + formatDate(today, DISPLAY_DATE_FORMAT)
        );


        // Initialize Adapter

        studentAdapter = new StudentAdapter(
                students,
                (student, newStatus) -> {

                    boolean saved = databaseHelper.markAttendance(
                            student.getId(),
                            selectedDate,
                            newStatus
                    );

                    if (saved) {

                        student.setStatus(newStatus);

                        updateAttendanceSummary();

                        Toast.makeText(
                                this,
                                "Attendance saved successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                this,
                                "Failed to save attendance",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    return saved;
                }
        );


        // Configure RecyclerView

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerView.setAdapter(studentAdapter);


        // Load Today's Attendance

        loadAttendanceForDate(selectedDate);


        // Add Student Button

        addButton.setOnClickListener(v -> {

            String name = studentName.getText().toString().trim();

            if (name.isEmpty()) {

                studentName.setError("Please enter a student name");

                return;
            }

            long studentId = databaseHelper.addStudent(name);

            if (studentId == -1) {

                Toast.makeText(
                        this,
                        "Failed to save student",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Student newStudent = new Student(studentId, name);

            students.add(newStudent);

            int newPosition = students.size() - 1;

            studentAdapter.notifyItemInserted(newPosition);

            recyclerView.scrollToPosition(newPosition);

            studentName.setText("");

            updateAttendanceSummary();

        });


        // Choose Date Button

        selectDateButton.setOnClickListener(v -> showDatePicker());


        // Handle System Window Insets

        View mainView = findViewById(R.id.main);

        final int initialLeft = mainView.getPaddingLeft();
        final int initialTop = mainView.getPaddingTop();
        final int initialRight = mainView.getPaddingRight();
        final int initialBottom = mainView.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(
                mainView,
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            initialLeft + systemBars.left,
                            initialTop + systemBars.top,
                            initialRight + systemBars.right,
                            initialBottom + systemBars.bottom
                    );

                    return insets;
                }
        );

    }


    // Format Date

    private String formatDate(Date date, String pattern) {

        return new SimpleDateFormat(
                pattern,
                Locale.US
        ).format(date);

    }


    // Show Date Picker

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        try {

            Date parsedDate = new SimpleDateFormat(
                    DB_DATE_FORMAT,
                    Locale.US
            ).parse(selectedDate);

            if (parsedDate != null) {

                calendar.setTime(parsedDate);
            }

        } catch (ParseException e) {

            android.util.Log.e(
                    "MainActivity",
                    "Failed to parse selected date",
                    e
            );
        }


        DatePickerDialog datePickerDialog = new DatePickerDialog(

                this,

                (view, year, month, dayOfMonth) -> {

                    Calendar selectedCalendar = Calendar.getInstance();

                    selectedCalendar.set(
                            year,
                            month,
                            dayOfMonth,
                            0,
                            0,
                            0
                    );

                    selectedCalendar.set(
                            Calendar.MILLISECOND,
                            0
                    );

                    Date chosenDate = selectedCalendar.getTime();


                    // Update Selected Date

                    selectedDate = formatDate(
                            chosenDate,
                            DB_DATE_FORMAT
                    );


                    // Update Date TextView

                    txtSelectedDate.setText(
                            "Date: " + formatDate(
                                    chosenDate,
                                    DISPLAY_DATE_FORMAT
                            )
                    );


                    // Load Attendance For Selected Date

                    loadAttendanceForDate(selectedDate);

                },

                calendar.get(Calendar.YEAR),

                calendar.get(Calendar.MONTH),

                calendar.get(Calendar.DAY_OF_MONTH)

        );

        datePickerDialog.show();

    }


    // Load Attendance From SQLite

    private void loadAttendanceForDate(String date) {

        HashMap<Long, Student.Status> attendance =
                databaseHelper.getAttendanceByDate(date);


        // Update Every Student's Status

        for (Student student : students) {

            Student.Status status = attendance.getOrDefault(
                    student.getId(),
                    Student.Status.NOT_MARKED
            );

            student.setStatus(status);

        }


        // Refresh RecyclerView

        if (studentAdapter != null) {

            studentAdapter.notifyDataSetChanged();

        }


        // Update Attendance Statistics

        updateAttendanceSummary();

    }


    // Calculate Attendance Summary

    private void updateAttendanceSummary() {

        int present = 0;

        int absent = 0;

        int notMarked = 0;


        for (Student student : students) {

            switch (student.getStatus()) {

                case PRESENT:

                    present++;

                    break;


                case ABSENT:

                    absent++;

                    break;


                case NOT_MARKED:

                    notMarked++;

                    break;

            }

        }


        // Update Attendance Counts

        txtPresentCount.setText(
                "Present: " + present
        );

        txtAbsentCount.setText(
                "Absent: " + absent
        );

        txtNotMarkedCount.setText(
                "Not Marked: " + notMarked
        );


        // Calculate Attendance Percentage

        int totalMarked = present + absent;

        if (totalMarked == 0) {

            txtAttendanceRate.setText("Rate: N/A");

        } else {

            double attendanceRate =
                    (present * 100.0) / totalMarked;

            txtAttendanceRate.setText(
                    String.format(
                            Locale.US,
                            "Rate: %.1f%%",
                            attendanceRate
                    )
            );

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
