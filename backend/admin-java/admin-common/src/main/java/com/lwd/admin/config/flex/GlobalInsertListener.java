package com.lwd.admin.config.flex;

import com.lwd.admin.base.BaseEntity;
import com.mybatisflex.annotation.InsertListener;

import java.time.LocalDateTime;

/**
 * 全局插入监听
 *
 * @author lwd
 */
public class GlobalInsertListener implements InsertListener {
    @Override
    public void onInsert(Object obj) {
        // 校验写权限，无权限则置空
        // 插入时要插入当前时间和当前用户
        if (obj instanceof BaseEntity entity) {
            entity.setCreateDateTime(LocalDateTime.now());
        }
    }
}
