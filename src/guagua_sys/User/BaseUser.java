package guagua_sys.User;

import java.sql.Time;
import java.time.LocalDateTime;

/**
 * 用户基类
 */
public abstract class BaseUser {
    /** 用户ID */
    private Long id;
    /** 用户名 */
    private String username;
    /** 密码(加密存储) */
    private String password;
    /** 角色 */
    private String role;
    /** 创建时间 */
    private LocalDateTime createTime;
    /** 更新时间 */
    private LocalDateTime updateTime;
    /** 状态(0-正常,1-禁用) */
    private Integer status;

    public BaseUser(String name, String password, String role, int status, LocalDateTime time) {
        this.username = name;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createTime = time;
        this.updateTime = time;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public Integer getStatus() {
        return status;
    }
}