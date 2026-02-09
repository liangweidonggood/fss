create table sys_tenant (
    id                   INT8                 not null,
    tenant_name          VARCHAR(100)         not null,
    tenant_domain        VARCHAR(100)         not null,
    tenant_code          VARCHAR(100)         not null,
    admin_phone          VARCHAR(50)          not null,
    expire_time          timestamp            not null,
    tenant_encrypt       TEXT                 not null,
    remark               VARCHAR(255)         not null,
    create_date_time     timestamp            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date_time     timestamp            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_user_id       INT8                 not null,
    update_user_id       INT8                 not null,
    is_deleted           INT4                 not null default 0,
    version              INT4                 not null default 0,
    PRIMARY KEY (id)
);
comment on table  sys_tenant is '租户表';
comment on column sys_tenant.id is '主键id';
comment on column sys_tenant.tenant_name is '租户名称';
comment on column sys_tenant.tenant_domain is '租户域名';
comment on column sys_tenant.tenant_code is '租户编码';
comment on column sys_tenant.admin_phone is '管理员手机号';
comment on column sys_tenant.expire_time is '租户过期时间,只展示用';
comment on column sys_tenant.tenant_encrypt is '租户信息密存储';
comment on column sys_tenant.remark is '备注';
comment on column sys_tenant.create_date_time is '创建时间';
comment on column sys_tenant.update_date_time is '更新时间';
comment on column sys_tenant.create_user_id is '创建人id';
comment on column sys_tenant.update_user_id is '更新人id';
comment on column sys_tenant.is_deleted is '是否删除,0否1是,默认0';
comment on column sys_tenant.version is '版本号';
CREATE UNIQUE INDEX uk_sys_tenant_tenant_code ON sys_tenant (tenant_code) WHERE is_deleted = 0;
CREATE UNIQUE INDEX uk_sys_tenant_tenant_domain ON sys_tenant (tenant_domain) WHERE is_deleted = 0;
