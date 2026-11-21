package com.example.studentattendancemanagerjava;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat   .app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import android.text.TextUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class MainActivity extends AppCompatActivity {

    private final ArrayList<Student> students = new ArrayList<>();
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        try {
    Class.forName("androidx.recyclerview.widget.RecyclerView");

    android.util.Log.d(
        "RecyclerTest",
        "RecyclerView class loaded successfully"
    );

} catch (Throwable e) {
    android.util.Log.e(
        "RecyclerTest",
        "Failed to load RecyclerView",
        e
    );
}
        setContentView(R.layout.activity_main);
        
        // TextView prTest = findViewById(R.id.textView);
        
        EditText studentName = findViewById(R.id.editStudent);

        Button addButton = findViewById(R.id.btnWelcome);
        RecyclerView recyclerView = findViewById(R.id.recyclerStudent);

        studentAdapter = new StudentAdapter(students);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(studentAdapter);

        
        addButton.setOnClickListener(v -> {
            String name = studentName.getText().toString().trim();

            if (name.isEmpty()) {
                studentName.setError("please enter a student name");

                return;
            }

            Student newStudent = new Student(name);

            students.add(newStudent);

            int newPosition = students.size() - 1;
            studentAdapter.notifyItemInserted(newPosition);

            recyclerView.scrollToPosition(newPosition);

            
            studentName.setText("");
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}