package model;

public class Student {

    private int id;
    private String name;
    private String rollno;
    private String course;
    private String year;
    private String phone;
    private String room;

    public Student(int id, String name, String rollno, String course, String year, String phone, String room) {
        this.id = id;
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.year = year;
        this.phone = phone;
        this.room = room;
    }

    public Student(String name, String rollno, String course, String year, String phone, String room) {
        this.name = name;
        this.rollno = rollno;
        this.course = course;
        this.year = year;
        this.phone = phone;
        this.room = room;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollno() { return rollno; }
    public String getCourse() { return course; }
    public String getYear() { return year; }
    public String getPhone() { return phone; }
    public String getRoom() { return room; }
}
