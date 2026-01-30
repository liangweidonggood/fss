package com.lwd.admin.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Timestamp;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表 实体类。
 *
 * @author lwd
 * @since 2026-01-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("auth_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    /**
     * 创建时间
     */
    private Timestamp createdDate;

    /**
     * 创建人id
     */
    private Long createdBy;

    /**
     * 修改时间
     */
    private Timestamp updatedDate;

    /**
     * 修改人id
     */
    private Long updatedBy;

    private String username;

    private String password;

    private Integer permVersion;

    /**
     * 账号是否可用1 表示启用，0 表示禁用
     */
    private Integer isEnabled;

}
