package com.lwd.admin.config.flex;

import com.mybatisflex.annotation.SetListener;

/**
 * 全局设置监听器
 * 字段权限处理
 *
 * @author lwd
 */
public class GlobalOnSetListener implements SetListener {
    @Override
    public Object onSet(Object entity, String property, Object value) {
        // 无权限返回 null，有权限返回原值
        return value;
    }
}
