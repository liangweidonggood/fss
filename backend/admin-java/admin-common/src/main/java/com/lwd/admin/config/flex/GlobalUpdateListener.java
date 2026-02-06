package com.lwd.admin.config.flex;

import com.lwd.admin.base.BaseEntity;
import com.mybatisflex.annotation.UpdateListener;

import java.time.LocalDateTime;

/**
 * 全局更新监听
 *
 * @author lwd
 */
public class GlobalUpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object obj) {
        // 校验写权限，无权限则置空
        // 更新时要插入当前时间和当前用户
        if (obj instanceof BaseEntity entity) {
            entity.setUpdateDateTime(LocalDateTime.now());
        }
    }
}
