package com.lwd.admin.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 用户表 表定义层。
 *
 * @author lwd
 * @since 2026-01-30
 */
public class UserTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户表
     */
    public static final UserTableDef USER = new UserTableDef();

    
    public final QueryColumn ID = new QueryColumn(this, "id");

    
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 创建人id
     */
    public final QueryColumn CREATED_BY = new QueryColumn(this, "created_by");

    /**
     * 账号是否可用1 表示启用，0 表示禁用
     */
    public final QueryColumn IS_ENABLED = new QueryColumn(this, "is_enabled");

    /**
     * 修改人id
     */
    public final QueryColumn UPDATED_BY = new QueryColumn(this, "updated_by");

    /**
     * 创建时间
     */
    public final QueryColumn CREATED_DATE = new QueryColumn(this, "created_date");

    
    public final QueryColumn PERM_VERSION = new QueryColumn(this, "perm_version");

    /**
     * 修改时间
     */
    public final QueryColumn UPDATED_DATE = new QueryColumn(this, "updated_date");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CREATED_DATE, CREATED_BY, UPDATED_DATE, UPDATED_BY, USERNAME, PASSWORD, PERM_VERSION, IS_ENABLED};

    public UserTableDef() {
        super("", "auth_user");
    }

    private UserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableDef("", "auth_user", alias));
    }

}
