package com.lwd.admin.config.flex;

import com.mybatisflex.core.dialect.LimitOffsetProcessor;
import com.mybatisflex.core.dialect.OperateType;
import com.mybatisflex.core.dialect.impl.CommonsDialectImpl;
import com.mybatisflex.core.dialect.KeywordWrap;
import com.mybatisflex.core.table.TableInfo;

/**
 * 自定义权限数据库方言
 *
 * @author lwd
 */
public class DataAuthDialectImpl extends CommonsDialectImpl {

    /**
     * 构造函数
     */
    public DataAuthDialectImpl() {
        super(KeywordWrap.NONE, LimitOffsetProcessor.POSTGRESQL);
    }

    @Override
    public void prepareAuth(TableInfo tableInfo, StringBuilder sql, OperateType operateType) {
        super.prepareAuth(tableInfo, sql, operateType);
    }
}

