package com.lwd.admin.core.system.entity;

import com.lwd.admin.base.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 用户表 实体类。
 *
 * @author lwd
 * @since 2026-02-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "sys_user", schema = "fss_master")
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键,默认使用雪花id
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 权限版本（初始为1，权限变更时自增）
     */
    private Integer perVersion;

    /**
     * 所属组织ID
     */
    private Long orgId;

    /**
     * 账号类型1内部2外部
     */
    private Integer accountType;

    /**
     * 用户状态 0正常 1禁用(人工) 2锁定(密码错误/风险)
     */
    private Integer userStatus;

    /**
     * 密码错误次数
     */
    private Integer pwdErrorCount;

    /**
     * 账户过期时间（null=永不过期）
     */
    private LocalDateTime accountExpireTime;

    /**
     * 密码过期时间（null=永不过期）
     */
    private LocalDateTime credentialsExpireTime;

    /**
     * 锁定原因（如：密码错误5次/异地登录）
     */
    private String lockedReason;

    /**
     * 锁定时间（审计用）
     */
    private LocalDateTime lockedTime;

}
