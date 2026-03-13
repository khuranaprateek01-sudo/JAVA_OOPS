package com.course.main;
import com.course.model.*;
import com.course.service.*;
import com.course.exception.*;
import java.util.Scanner;
public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CourseService service = new CourseService();
        int choice;
        do {
            System.out.println("\n1 Add Course");
            System.out.println("2 Enroll Student");
            System.out.println("3 View Courses");
            System.out.println("4 View Enrollment File");
            System.out.println("5 Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Course ID: ");
                    int cid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Course Name: ");
                    String cname = sc.nextLine();
                    System.out.print("Max Seats: ");
                    int seats = sc.nextInt();
                    course c = new course(cid, cname, seats);
                    service.addCourse(c);
                    break;
                    case 2:
                        try {
                            System.out.print("Course ID: ");
                        int courseId = sc.nextInt();
                        System.out.print("Student ID: ");
                        int sid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Student Name: ");
                        String sname = sc.nextLine();
                        Student s = new Student(sid, sname);
                        service.enrollStudent(courseId, s);

                    } catch (CourseFullException |
                             CourseNotFoundException |
                             DuplicateEnrollmentException e) {
                            System.out.println(e.getMessage());
                    }
                        break;

                case 3:
                    service.viewCourses();
                    break;

                case 4:
                    service.viewFileData();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);

        sc.close();
    }
}