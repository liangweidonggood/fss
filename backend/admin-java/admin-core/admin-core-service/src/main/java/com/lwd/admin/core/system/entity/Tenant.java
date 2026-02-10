package com.lwd.admin.core.system.entity;

import com.lwd.admin.base.BaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户表 实体类。
 *
 * @author lwd
 * @since 2026-02-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("sys_tenant")
public class Tenant extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    private Long id;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户域名
     */
    private String tenantDomain;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 管理员手机号
     */
    private String adminPhone;

    /**
     * 租户过期时间,只展示用
     */
    private LocalDateTime expireTime;

    /**
     * 租户信息密存储
     */
    private String tenantEncrypt;

    /**
     * 备注
     */
    private String remark;

}
