
package com.example.studentattendancemanagerjava;

public class StudentAttendanceReport {

    private long studentId;
    private String studentName;

    private int presentCount;
    private int absentCount;
    private int totalMarked;

    public StudentAttendanceReport(
            long studentId,
            String studentName,
            int presentCount,
            int absentCount,
            int totalMarked) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.totalMarked = totalMarked;
    }

    public long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int getTotalMarked() {
        return totalMarked;
    }

    public double getAttendanceRate() {

        if (totalMarked == 0) {
            return 0.0;
        }

        return (presentCount * 100.0) / totalMarked;
    }
}
