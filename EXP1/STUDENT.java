import java.util.*;
class student
{ 
    int sec;
    student()
    {
        sec=243;
        System.out.println("Section is"+sec);
    }
    student(int rollno,String name)
    {
        System.out.println("roll no is"+rollno);
        System.out.println("name is "+name);
    }
}
public class Main {
    public static void main (String args[])
    {
        Scanner sc= new Scanner(System.in);
        int a;
        System.out.println("enter roll no of student");
        a=sc.nextInt();
        String s;
        System.out.println("enter student name");
        s=sc.next();
        student s1=new student(a,s);
        student s2 =new student();
    }
}
   