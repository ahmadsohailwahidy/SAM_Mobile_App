package com.example.studentattendancemanagerjava;

public class Student {
    
    public enum Status {
        NOT_MARKED,
        PRESENT,
        ABSENT
    }

    private String name;
    private Status status;

    public Student(String name){
        this.name=name;
        this.status=Status.NOT_MARKED;
    }

    public String getName(){
        return name;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
