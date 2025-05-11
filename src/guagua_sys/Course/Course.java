package guagua_sys.Course;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 */
public class Course {
    /** 课程ID */
    private Long id;
    /** 课程编号 */
    private String courseNo;
    /** 课程名称 */
    private String courseName;
    /** 授课教师ID */
    private Long teacherId;
    /** 适用专业ID */
    private Long majorId;
    /** 适用年级 */
    private Integer grade;
    /** 课程性质(1-必修,2-选修) */
    private Integer courseType;
    /** 学分 */
    private BigDecimal credit;
    /** 是否公开(0-否,1-是) */
    private Integer isPublic;
    /** 课程状态(1-已提交,2-审核通过,3-审核不通过,4-公开,5-隐藏) */
    private Integer status;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;

    // getter setter 方法省略
}