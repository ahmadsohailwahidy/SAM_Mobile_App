package com.example.studentattendancemanagerjava;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteException;

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
                COL_ATTENDANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    public long addStudent(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_STUDENT_NAME, name);

        long studentId = db.insert(
                TABLE_STUDENTS,
                null,
                values
        );

        return studentId;
    }


    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
            COL_STUDENT_ID,
            COL_STUDENT_NAME
        };



        ///SELECT id, name
        ///FROM students
        ///ORDER BY id ASC;

        try (Cursor cursor = db.query(
            TABLE_STUDENTS,
            columns,
            null,
            null,
            null,
            null,
            COL_STUDENT_ID + " ASC"
        )) {
            while(cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_NAME));

                Student student = new Student(id, name);

                students.add(student);

            }
        }

        return students;
    }

    public boolean markAttendance(long studentId, String date, Student.Status status) {
        if (studentId <= 0 || date == null || date.trim().isEmpty() || status == null || status == Student.Status.NOT_MARKED) {
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_STUDENT_FK, studentId);
        values.put(COL_ATTENDANCE_DATE, date);
        values.put(COL_STATUS, status.name());

        db.beginTransaction();

        try {

            int updateRows = db.update(
                TABLE_ATTENDANCE,
                values,
                COL_STUDENT_FK + " = ? AND " +
                        COL_ATTENDANCE_DATE + " = ?",
                new String[]{
                    String.valueOf(studentId),
                    date
                }
            );

            if(updateRows == 0) {

                long newRowId = db.insert(
                    TABLE_ATTENDANCE,
                    null,
                    values
                );

                if (newRowId == -1) {
                    return false;
                }
            }

            db.setTransactionSuccessful();

            return true;
        } catch (SQLiteException e) {
            android.util.Log.e(
                "DatabaseHelper",
                "Failed to save attendance"
            );

            return false;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Database Migration Logic will added when the
        //database schema changes.
    }

    

}
