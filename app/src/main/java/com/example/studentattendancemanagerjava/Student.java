package com.example.studentattendancemanagerjava;

public class Student {
    
    public enum Status {
        NOT_MARKED,
        PRESENT,
        ABSENT
    }

    private long id;
    private String name;
    private Status status;

    public Student(String name){
        this(-1, name, Status.NOT_MARKED);
    }

    public Student(long id , String name) {
        this(id, name, Status.NOT_MARKED);
    }

    public Student(long id, String name, Status status){
        this.id = id;
        this.name=name;
        this.status=status;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
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
