package com.lwd.admin.base;

import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体基类
 *
 * @author lwd
 */
@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private Long updateUserId;
    private Long createUserId;
    private Integer isDeleted;
    private Integer version;
}
