package guagua_sys.User;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * 教师实体类
 */
public class Teacher extends BaseUser {
    /** 教师工号 */
    private String teacherNo;
    /** 所属院系ID */
    private Long departmentId;
    /** 教师姓名 */
    private String name;
    /** 联系电话 */
    private String phone;
    /** 邮箱 */
    private String email;

    public Teacher(String name, String password, String role, int status, LocalDateTime time){
        super(name, password, role, status, time);
    }


}
