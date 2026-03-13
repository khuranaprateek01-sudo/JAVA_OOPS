package com.course.service;
import com.course.model.course;
import com.course.model.Student;
import com.course.exception.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    public List<course> courses = new ArrayList<>();
    public void addCourse(course c) {
        courses.add(c);
        System.out.println("Course added: " + c.getCourseName());
        safe();
    }
    public void enrollStudent(int id, Student s)
            throws CourseFullException, CourseNotFoundException, DuplicateEnrollmentException {
        course c = null;
        for (course course : courses) {
            if (course.getCourseId() == id) {
                c = course;
                break;
            }
        }
        if (c == null) throw new CourseNotFoundException("Course not found");

        for (Student st : c.getEnrolledStudents()) {
            if (st.getStudentId() == s.getStudentId())
                throw new DuplicateEnrollmentException("Student already enrolled");
        }
        if (c.getEnrolledStudents().size() >= c.getMaxSeats())
            throw new CourseFullException("Course full");

        c.getEnrolledStudents().add(s);
        System.out.println(s.getStudentName() + " enrolled in " + c.getCourseName());
        safe();
    }
    public void viewCourses() {
        for (course c : courses) {
            c.displayCourse();
        }
    }
    public void safe() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("example.txt"));
            for (course c : courses) {
                bw.write("Course: " + c.getCourseId() + "," + c.getCourseName() + "," + c.getMaxSeats());
                bw.newLine();
                for (Student s : c.getEnrolledStudents()) {
                    bw.write("Student: " + s.getStudentId() + "," + s.getStudentName());
                    bw.newLine();
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void viewFileData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("example.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}


