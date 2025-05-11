package guagua_sys.Login;

import guagua_sys.User.BaseUser;
import guagua_sys.sql.DBinit;

import java.sql.Time;

public class Login {
    //注册
    public static void register(BaseUser newUser) {
        if(DBinit.checkName(newUser.getUsername())){
            System.out.println("用户已存在或注册失败");
        }
        else{
            //将用户添加到mysql中
            DBinit.insertUser(newUser);
            System.out.println("注册成功");
        }
    }

    //登录
    public static void login(String name, String password) {
        if (DBinit.checkName(name)){
            if (DBinit.checkPassword(name, password)){
                System.out.println("登录成功");
            }
            else{
                System.out.println("登录失败");
            }
        }
    }
}
