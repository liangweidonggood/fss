package com.lwd.admin.system.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 *  表定义层。
 *
 * @author lwd
 * @since 2026-02-03
 */
public class UserTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String TABLE_NAME = "auth_user";

    /**
     * 表说明: 
     */
    public static final UserTableDef USER = new UserTableDef();

    
    public static final QueryColumn ID = new QueryColumn(TABLE_NAME, "id");

    
    public static final QueryColumn PASSWORD = new QueryColumn(TABLE_NAME, "password");

    
    public static final QueryColumn USERNAME = new QueryColumn(TABLE_NAME, "username");

    /**
     * 创建人id
     */
    public static final QueryColumn CREATED_BY = new QueryColumn(TABLE_NAME, "created_by");

    /**
     * 账号是否可用1 表示启用，0 表示禁用
     */
    public static final QueryColumn IS_ENABLED = new QueryColumn(TABLE_NAME, "is_enabled");

    /**
     * 修改人id
     */
    public static final QueryColumn UPDATED_BY = new QueryColumn(TABLE_NAME, "updated_by");

    /**
     * 创建时间
     */
    public static final QueryColumn CREATED_DATE = new QueryColumn(TABLE_NAME, "created_date");

    
    public static final QueryColumn PERM_VERSION = new QueryColumn(TABLE_NAME, "perm_version");

    /**
     * 修改时间
     */
    public static final QueryColumn UPDATED_DATE = new QueryColumn(TABLE_NAME, "updated_date");

    /**
     * 所有字段。
     */
    public static final QueryColumn ALL_COLUMNS = new QueryColumn(TABLE_NAME, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public static final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY, USERNAME, PASSWORD, PERM_VERSION, IS_ENABLED};

    public UserTableDef() {
        super("", TABLE_NAME);
    }

    private UserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    @Override
    public UserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableDef("", TABLE_NAME, alias));
    }

}
