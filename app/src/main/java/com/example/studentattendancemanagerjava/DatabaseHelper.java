package com.example.studentattendancemanagerjava;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME="student_attendance.db";

    private static final int DATABASE_VERSION=1;

    // Tables
    // Student Table
    public static final String TABLE_STUDENTS = "students";

    public static final String COL_STUDENT_ID = "id";

    public static final String COL_STUDENT_NAME = "name";

    //Attendance Table
    public static final String TABLE_ATTENDANCE = "attendance";

    public static final String COL_ATTENDANCE_ID = "id";
    public static final String COL_STUDENT_FK = "student_id";
    public static final String COL_ATTENDANCE_DATE = "attendance_date";
    public static final String COL_STATUS = "status";


    /////////////////////
    
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){

        super.onConfigure(db);

        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String createStudentsTable = 
                "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COL_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_NAME + " TEXT NOT NULL" + ")";
        
        db.execSQL(createStudentsTable);

        String createAttendanceTable =
                "CREATE TABLE " + TABLE_ATTENDANCE + " (" +
                COL_ATTENDANCE_ID + " INEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_FK + " INTEGER NOT NULL, " +
                COL_ATTENDANCE_DATE + " TEXT NOT NULL, " +
                COL_STATUS + " TEXT NOT NULL " +
                "CHECK(" + COL_STATUS + " IN ('PRESENT', 'ABSENT')), " +
                "FOREIGN KEY(" + COL_STUDENT_FK + ") " +
                "REFERENCES " + TABLE_STUDENTS + 
                "(" + COL_STUDENT_ID + ") ON DELETE CASCADE, " +
                "UNIQUE(" + COL_STUDENT_FK + ", " +
                COL_ATTENDANCE_DATE + ")" + ")";

        db.execSQL(createAttendanceTable);

        String createDateIndex = 
                "CREATE INDEX index_attendance_date " +
                "ON " + TABLE_ATTENDANCE +
                "(" + COL_ATTENDANCE_DATE + ")";
        
        db.execSQL(createDateIndex);
                
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Database Migration Logic will added when the
        //database schema changes.
    }


}
