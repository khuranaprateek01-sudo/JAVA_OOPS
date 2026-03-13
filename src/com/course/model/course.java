package com.course.model;
import java.util.ArrayList;
import java.util.List;

public class course {
        private int courseId;
        private String courseName;
        private int maxSeats;
        private List<Student> enrolledStudents;

    public course(int courseId, String courseName, int maxSeats) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.maxSeats = maxSeats;
        this.enrolledStudents = new ArrayList<>();
    }
    public int getCourseId()
    { return courseId; }
    public void setCourseId(int courseId)
    { this.courseId = courseId; }

    public String getCourseName()
    { return courseName; }
    public void setCourseName(String courseName)
    { this.courseName = courseName; }

    public int getMaxSeats()
    { return maxSeats; }
    public void setMaxSeats(int maxSeats)
    { this.maxSeats = maxSeats; }

    public List<Student> getEnrolledStudents() { return enrolledStudents; }

    public void displayCourse() {
        System.out.println("Course id is" + courseId);
        System.out.println("Course Name: " + courseName);
        System.out.println("Max Seats: " + maxSeats);
        System.out.println("Enrolled Students: " + enrolledStudents.size());
        for (Student s : enrolledStudents) {
            System.out.println(s.getStudentId() + " - " + s.getStudentName());
        }}
}
