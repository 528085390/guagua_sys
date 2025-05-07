import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// 定义一个简单的 Student 类来表示学生对象 (这部分和之前的例子一样)
class Student {
    private int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(String name, int age) { // 用于插入时，id 由数据库自动生成
        this.name = name;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "学生信息 [ID=" + id + ", 姓名=" + name + ", 年龄=" + age + "]";
    }
}

public class SimpleStudentMySQLApp {

    // 1. 定义 MySQL 数据库连接信息
    //    !! 非常重要：请将下面的占位符替换为您自己的 MySQL 信息 !!
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    //   - "jdbc:mysql://" : 这是连接 MySQL 的 JDBC URL 前缀。
    //   - "localhost:3306" : 您的 MySQL 服务器地址和端口。如果 MySQL 在本机且使用默认端口，通常就是这个。
    //   - "/my_small_project_db" : 您在 MySQL 中创建的数据库名称。请务必修改为您自己的数据库名!
    //   - "?useSSL=false" :  对于本地开发，通常设置为 false 来禁用 SSL 连接，简化配置。
    //   - "&serverTimezone=UTC" : 建议设置，以避免时区问题。
    //   - "&allowPublicKeyRetrieval=true" : 对于某些新版本的 MySQL 和驱动，如果遇到认证问题可能需要这个参数。

    private static final String USER = "root"; // !! 替换为您的 MySQL 用户名 !!
    private static final String PASSWORD = "123456"; // !! 替换为您的 MySQL 密码 !!

    public static void main(String[] args) {
        System.out.println("--- 开始运行 Java MySQL 示例 ---");
        try {
            // 2. (可选步骤) 显式加载驱动程序。对于现代 JDBC (4.0+) 和正确配置了依赖的项目，这通常是自动的。
            // Class.forName("com.mysql.cj.jdbc.Driver");

            // 3. 创建数据库表 (如果表不存在的话)
            createStudentTable();

            // 4. 添加一些学生记录
            System.out.println("\n--- 尝试添加学生 ---");
            addStudent(new Student("张三", 20));
            addStudent(new Student("李四", 22));
            addStudent(new Student("王五", 19));

            // 5. 查询并显示所有学生记录
            System.out.println("\n--- 尝试查询所有学生 ---");
            List<Student> students = getAllStudents();
            System.out.println("\n数据库中的所有学生信息:");
            if (students.isEmpty()) {
                System.out.println("目前数据库中没有学生信息。");
            } else {
                for (Student student : students) {
                    System.out.println(student);
                }
            }

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            e.printStackTrace();
        }
        // catch (ClassNotFoundException e) {
        //     System.err.println("MySQL JDBC 驱动未找到: " + e.getMessage());
        //     e.printStackTrace();
        // }
        System.out.println("\n--- Java MySQL 示例运行结束 ---");
    }

    // 方法：获取数据库连接
    private static Connection getConnection() throws SQLException {
        // DriverManager.getConnection() 方法尝试根据提供的 URL、用户名和密码建立一个数据库连接。
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // 方法：创建 students 表 (如果不存在)
    public static void createStudentTable() throws SQLException {
        // 这是创建表的 SQL 语句。
        // "IF NOT EXISTS" 确保如果表已经存在，则不会尝试再次创建，避免出错。
        // "id INT PRIMARY KEY AUTO_INCREMENT" 定义 id 为整数类型，是表的主键，并且会自动递增。
        // "name VARCHAR(255) NOT NULL" 定义 name 为字符串，最大长度255，且不能为空。
        // "age INT" 定义 age 为整数。
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "age INT" +
                ")";

        // "try-with-resources" 语句确保 Connection 和 Statement 在使用完毕后被自动关闭。
        // 这是推荐的做法，可以防止资源泄漏。
        try (Connection connection = getConnection(); // 1. 获取连接
             Statement statement = connection.createStatement()) { // 2. 创建 Statement 对象来执行静态 SQL

            // 3. 执行 SQL 语句来创建表
            statement.executeUpdate(createTableSQL);
            System.out.println("通知: 'students' 表已在 MySQL 中成功创建或已存在。");
        }
        // 当代码块结束时，connection 和 statement 会被自动关闭。
    }

    // 方法：添加一个学生记录
    public static void addStudent(Student student) throws SQLException {
        // 拼接插入语句（不安全，仅供演示）
        String insertSQL = "INSERT INTO students (name, age) VALUES ('" + student.getName() + "', " + student.getAge() + ")";

        // 获取连接并创建 Statement 对象
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // 执行插入操作
            int rowsAffected = statement.executeUpdate(insertSQL);

            // 判断是否插入成功
            if (rowsAffected == 1) {
                System.out.println("学生数据插入成功！");
            } else {
                System.out.println("学生数据插入失败！");
            }
        }
    }

    // 方法：获取所有学生记录
    public static List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        // 这是查询数据的 SQL 语句。
        // "SELECT id, name, age FROM students" 从 students 表中选择 id, name, age 这三列。
        // 明确列出列名通常比使用 "SELECT *" 更好。
        String selectSQL = "SELECT id, name, age FROM students";

        try (Connection connection = getConnection(); // 1. 获取连接
             Statement statement = connection.createStatement(); // 2. 创建 Statement 对象
             // 3. 执行查询。executeQuery() 方法用于 SELECT 语句，并返回一个 ResultSet 对象。
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            // 4. 遍历 ResultSet (结果集)
            //    resultSet.next() 将光标移动到结果集的下一行。如果存在下一行，则返回 true。
            while (resultSet.next()) {
                // 5. 从当前行中获取每一列的值。
                //    推荐使用列名 (如 "id", "name") 来获取值，这样更清晰且不易出错。
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                // 6. 用获取到的数据创建一个 Student 对象，并将其添加到列表中。
                studentList.add(new Student(id, name, age));
            }
        }
        return studentList; // 返回包含所有学生对象的列表
    }

    //删除学生记录
    public static void deleteStudent(int id) throws SQLException {
        String deleteSQL = "DELETE FROM students WHERE id = " + id;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSQL);
            if (rowsAffected == 1) {
                System.out.println("学生数据删除成功！");
            } else {
                System.out.println("学生数据删除失败！");
            }
        }

    }
}

