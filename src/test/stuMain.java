package test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class stuMain {
    public static void main(String[] args) throws SQLException {
        sql.Insert(new Student("张三", "男", 20));
        sql.Insert(new Student("王五", "女", 19));
        sql.Insert(new Student("赵六", "男", 18));
        ArrayList<Student> allStudents = sql.getAllStudents();
        for (Student stu : allStudents){
            System.out.println(stu);
        }

    }
}
