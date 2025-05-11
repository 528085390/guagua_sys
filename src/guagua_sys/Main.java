package guagua_sys;

import guagua_sys.Login.Login;
import guagua_sys.User.BaseUser;
import guagua_sys.User.Teacher;
import guagua_sys.sql.DBinit;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBinit db = new DBinit("root", "123456", "guagua_sys");
        Scanner in = new Scanner(System.in);
        while (true) {
            String name;
            String password;
            int role;

            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("3.退出");
            switch (in.nextInt()) {
                case 1:
                    System.out.println("请输入用户名");
                    name = in.next();
                    System.out.println("请输入密码");
                    password = in.next();
                    Login.login(name, password);
                    continue;
                case 2:
                    System.out.println("请输入用户名");
                    name = in.next();
                    System.out.println("请输入密码");
                    password = in.next();
                    System.out.println("请输入角色");
                    System.out.println("1.学生");
                    System.out.println("2.教师");
                    System.out.println("3.管理员");
                    role = in.nextInt();
                    switch (role) {
                        case 1:
                            BaseUser newStu = new BaseUser(name, password, "Student", 1, LocalDateTime.now()){};
                            break;
                        case 2:
                            BaseUser newTea = new Teacher(name, password, "Teacher", 1, LocalDateTime.now());
                            break;
                        case 3:
                            BaseUser newAdm = new Admin(name, password, "Admin", 1, LocalDateTime.now());
                           break;
                    }

                    Login.register();
                    continue;
                case 3:
                    return;
            }
        }

    }
}
