package test;

import java.sql.*;
import java.util.ArrayList;


public class sql {
    private static String user = "root";
    private static String password = "123456";
    private static String url = "jdbc:mysql://localhost:3306/stu";
    private static Statement op;
    static ArrayList<Student> studentList = new ArrayList<>();

    static {
        try {
            op = op();
            CreateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //connect mysql
    public static Statement op() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        return statement;
    }

    //create table
    public static void CreateTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS stu (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "sex CHAR(1), " +
                "age INT" +
                ")";
        op.executeUpdate(createTableSQL);
        System.out.println("Creat table successfully or already exist");
        flush();
    }

    //insert
    public static void Insert(Student newStu) throws SQLException {
        String addSQL = "INSERT INTO stu (name, sex, age) VALUES ('" + newStu.getName() + "', '" + newStu.getSex() + "', " + newStu.getAge() + ")";
        for (Student stu : studentList){
            if (stu.getName().equals(newStu.getName())){
                System.out.println("Student already exists!");
                return;
            }
        }
        op.executeUpdate(addSQL);
        System.out.println("Add successfully");
        flush();

    }

    //get all students
    public static ArrayList<Student> getAllStudents() throws SQLException {
        ArrayList<Student> studentList = new ArrayList<>();
        String selectSQL = "SELECT id, name, sex, age FROM stu";
        ResultSet resultSet = op.executeQuery(selectSQL);
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String sex = resultSet.getString("sex");
            int age = resultSet.getInt("age");
            studentList.add(new Student(id, name, sex, age));
        }
        return studentList;
    }
    private static void flush() throws SQLException {
        studentList.clear();
        studentList = getAllStudents();
    }



}
