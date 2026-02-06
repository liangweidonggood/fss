CREATE TABLE sys_user
(
    id                   INT8                 NOT NULL,
    username             varchar(100)         NOT NULL,
    password             varchar(100)         NOT NULL,
    phone                varchar(50)          NOT NULL,
    email                varchar(50)          NULL,
    real_name            varchar(100)         NOT NULL,
    per_version          INT4                 NOT NULL DEFAULT 1,
    org_id               INT8                 NOT NULL,
    account_type         INT4                 NOT NULL DEFAULT 1,
    create_date_time     timestamptz          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date_time     timestamptz          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_user_id       INT8                 not null,
    update_user_id       INT8                 not null,
    is_deleted           INT4                 not null default 0,
    version              INT4                 not null default 0,
    PRIMARY KEY (id)
);
COMMENT ON TABLE  sys_user IS '系统用户表';
COMMENT ON COLUMN sys_user.id IS '主键id';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.real_name IS '真实姓名';
COMMENT ON COLUMN sys_user.per_version IS '权限版本（初始为1，权限变更时自增）';
COMMENT ON COLUMN sys_user.org_id IS '所属组织id';
COMMENT ON COLUMN sys_user.account_type IS '账号类型1内部2外部';
comment on column sys_user.create_date_time is '创建时间';
comment on column sys_user.update_date_time is '更新时间';
comment on column sys_user.create_user_id is '创建人id';
comment on column sys_user.update_user_id is '更新人id';
comment on column sys_user.is_deleted is '是否删除,0否1是,默认0';
comment on column sys_user.version is '版本号';
