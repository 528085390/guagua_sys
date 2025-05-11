package guagua_sys.sql;

import guagua_sys.User.BaseUser;

import java.sql.*;
import java.time.LocalDateTime;

public class DBinit {
    private static String NAME;
    private static String PASSWORD;
    private static String DB;
    public static Statement op;


    public DBinit(String NAME, String PASSWORD, String DB) throws SQLException {
        this.NAME = NAME;
        this.PASSWORD = PASSWORD;
        this.DB = DB;
        op = init();
        CreateTable();

    }

    //链接数据库
    private Statement init() {
        try {
            String url = "jdbc:mysql://localhost:3306/" + DB + "?useUnicode=true&characterEncoding=utf8";
            Connection con = DriverManager.getConnection(url, NAME, PASSWORD);
            Statement op = con.createStatement();
            System.out.println("数据库连接成功");
            return op;

        } catch (Exception e) {
            System.out.println("数据库连接失败");
            return null;
        }
    }

    //建表
    private void CreateTable() throws SQLException {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS `t_user` (" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID'," +
                "  `username` varchar(50) NOT NULL COMMENT '用户名'," +
                "  `password` varchar(100) NOT NULL COMMENT '密码'," +
                "  `role` varchar(20) NOT NULL COMMENT '角色(ADMIN/TEACHER/STUDENT)'," +
                "  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0-正常,1-禁用)'," +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'," +
                "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE KEY `uk_username` (`username`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';";

        String createTeacherTableSQL = "CREATE TABLE IF NOT EXISTS `t_teacher` (" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID'," +
                "  `user_id` bigint NOT NULL COMMENT '用户ID'," +
                "  `teacher_no` varchar(20) NOT NULL COMMENT '教师工号'," +
                "  `department_id` bigint NOT NULL COMMENT '院系ID'," +
                "  `name` varchar(50) NOT NULL COMMENT '教师姓名'," +
                "  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话'," +
                "  `email` varchar(50) DEFAULT NULL COMMENT '邮箱'," +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'," +
                "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE KEY `uk_teacher_no` (`teacher_no`)," +
                "  KEY `idx_department_id` (`department_id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表'; ";

        String createCourseTableSQL = "CREATE TABLE IF NOT EXISTS `t_course` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
                "  `course_no` varchar(20) NOT NULL COMMENT '课程编号',\n" +
                "  `course_name` varchar(100) NOT NULL COMMENT '课程名称',\n" +
                "  `teacher_id` bigint NOT NULL COMMENT '授课教师ID',\n" +
                "  `major_id` bigint NOT NULL COMMENT '适用专业ID',\n" +
                "  `grade` tinyint NOT NULL COMMENT '适用年级',\n" +
                "  `course_type` tinyint NOT NULL COMMENT '课程性质(1-必修,2-选修)',\n" +
                "  `credit` decimal(3,1) NOT NULL COMMENT '学分',\n" +
                "  `is_public` tinyint NOT NULL DEFAULT '0' COMMENT '是否公开(0-否,1-是)',\n" +
                "  `status` tinyint NOT NULL COMMENT '课程状态(1-已提交,2-审核通过,3-审核不通过,4-公开,5-隐藏)',\n" +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `uk_course_no` (`course_no`),\n" +
                "  KEY `idx_teacher_id` (`teacher_id`),\n" +
                "  KEY `idx_major_id` (`major_id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';";

        op.executeUpdate(createUserTableSQL);
        op.executeUpdate(createCourseTableSQL);
        op.executeUpdate(createTeacherTableSQL);
        System.out.println("建立表格或已建立");

    }
    //查询用户名字
    public static boolean checkName(String name){
        try {
            String sql = "select * from t_user where username = '"+name+"'";
            ResultSet resultSet = op.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    //匹配用户密码
    public static boolean checkPassword(String name,String password){
        try {
            String sql = "select * from t_user where username = '"+name+"' and password = '"+password+"'";
            ResultSet resultSet = op.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //添加用户
    public static void insertUser(BaseUser newUser){
        String name = newUser.getUsername();
        String password = newUser.getPassword();
        String role = newUser.getRole();
        int status = newUser.getStatus();
        LocalDateTime createTime = newUser.getCreateTime();
        LocalDateTime updateTime = newUser.getUpdateTime();
        try {
            String sql = "insert into t_user(username,password,role,status,create_time,update_time) values('"+name+"','"+password+"','"+role+"',"+status+",'"+createTime+"','"+updateTime+"')";
            op.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }



}
