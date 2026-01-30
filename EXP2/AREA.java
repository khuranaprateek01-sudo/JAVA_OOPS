import java.util.*;
class student
{
    void area(int s)
    {
        System.out.println("area of square is"+s*s);
    }
    void area (int l,int b)
    {
        System.out.println("area of rectangle is"+l*b);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        int s;
        System.out.println("enter side of square");
        s=sc.nextInt();
        int l,b;
        System.out.println("enter length and breadth");
        l=sc.nextInt() ;
        b=sc.nextInt();
        student s1=new student();
        s1.area(s);
        s1.area(l,b);
        
    }
}