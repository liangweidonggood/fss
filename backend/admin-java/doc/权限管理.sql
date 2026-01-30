/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2025/11/21 13:38:46                          */
/*==============================================================*/


drop table if exists sys_company;

drop table if exists sys_form;

drop table if exists sys_form_field;

drop table if exists sys_menu;

drop table if exists sys_module;

drop table if exists sys_org;

drop table if exists sys_permission;

drop table if exists sys_post;

drop table if exists sys_post_role;

drop table if exists sys_role;

drop table if exists sys_role_data_scope;

drop table if exists sys_role_form_field;

drop table if exists sys_role_permission;

drop table if exists sys_user;

drop table if exists sys_user_post;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: sys_company                                           */
/*==============================================================*/
create table sys_company
(
    id                   bigint not null  comment '主键id',
    pid                  bigint  comment '父id',
    company_code         varchar(50)  comment '公司编码',
    company_name         varchar(50)  comment '公司名称',
    company_short_name   varchar(50)  comment '公司简称',
    company_nature       varchar(50)  comment '公司性质',
    company_outer_phone  varchar(50)  comment '外线电话',
    company_inner_phone  varchar(50)  comment '内线电话',
    company_fax          varchar(50)  comment '传真',
    company_postal_code  varchar(50)  comment '邮编',
    company_email        varchar(50)  comment '电子邮箱',
    company_owner_id     bigint  comment '负责人id',
    company_owner_name   varchar(50)  comment '负责人名称',
    company_address      varchar(255)  comment '详细地址',
    company_web_page     varchar(255)  comment '公司主页',
    company_founded_time datetime  comment '成立时间',
    company_business_scope varchar(255)  comment '经营范围',
    sort_num             int  comment '排序号',
    company_logo         varchar(255)  comment '公司logo',
    remark               varchar(255)  comment '备注',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_company comment '公司信息';

/*==============================================================*/
/* Table: sys_form                                              */
/*==============================================================*/
create table sys_form
(
    id                   bigint not null  comment '主键id',
    mod_id               bigint  comment '所属模块id',
    table_name           varchar(100)  comment '业务表名',
    form_name            varchar(100)  comment '表单名称',
    form_type            int  comment '表单类型',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_form comment '表单信息';

/*==============================================================*/
/* Table: sys_form_field                                        */
/*==============================================================*/
create table sys_form_field
(
    id                   bigint not null  comment '主键id',
    form_id              bigint  comment '表单id',
    field_name           varchar(100)  comment '字段名',
    field_label          varchar(100)  comment '字段标签',
    field_type           int  comment '字段类型',
    is_primary_key       int  comment '是否主键',
    is_foreign_key       int  comment '是否外键',
    foreign_table        varchar(100)  comment '外键关联表',
    foreign_field        varchar(100)  comment '外键关联字段',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_form_field comment '表单属性信息';

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
    id                   bigint not null  comment '主键id',
    mod_id               bigint  comment '所属模块id',
    pid                  bigint  comment '父id',
    menu_name            varchar(100)  comment '菜单名称',
    menu_type            int  comment '菜单类型',
    menu_path            varchar(100)  comment '菜单路径',
    menu_icon            varchar(255)  comment '菜单图标',
    sort_num             int  comment '排序号',
    is_form_perm         int  comment '是否配置表单权限（1-是 0-否）',
    form_id              bigint  comment '表单id',
    is_data_scope        int  comment '是否支持数据权限（1-是 0-否）',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_menu comment '菜单表';

/*==============================================================*/
/* Table: sys_module                                            */
/*==============================================================*/
create table sys_module
(
    id                   bigint not null  comment '主键id',
    pid                  bigint  comment '父模块',
    mod_name             varchar(100)  comment '模块名称',
    mod_code             varchar(100)  comment '模块代码',
    mod_level            int  comment '模块层级1系统级2功能级',
    mod_icon             varchar(255)  comment '模块图标',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_module comment '模块表';

/*==============================================================*/
/* Table: sys_org                                               */
/*==============================================================*/
create table sys_org
(
    id                   bigint not null  comment '主键id',
    pid                  bigint  comment '父级id',
    org_name             varchar(100)  comment '组织名称',
    org_code             varchar(100)  comment '组织编码',
    org_level            int  comment '组织层级1集团2公司3部门',
    org_type             int  comment '组织类型10集团20子公司30职能部门40业务部门50项目组',
    sort_num             int  comment '排序号',
    company_id           bigint  comment '公司id',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_org comment '组织机构';

/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
    id                   bigint not null  comment '主键id',
    menu_id              bigint  comment '菜单id',
    per_name             varchar(100)  comment '权限名称',
    per_flag             int  comment '权限标识',
    pid                  bigint  comment '父级id',
    per_type             int  comment '权限类型1菜单2按钮3api',
    per_url              varchar(100)  comment '权限url',
    sort_num             int  comment '排序号',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_permission comment '功能权限表';

/*==============================================================*/
/* Table: sys_post                                              */
/*==============================================================*/
create table sys_post
(
    id                   bigint not null  comment '主键id',
    org_id               bigint  comment '所属组织id',
    pid                  bigint  comment '父id',
    post_name            varchar(100)  comment '岗位名称',
    post_code            varchar(100)  comment '岗位编码',
    sort_num             int  comment '排序号',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_post comment '岗位表';

/*==============================================================*/
/* Table: sys_post_role                                         */
/*==============================================================*/
create table sys_post_role
(
    id                   bigint not null  comment '主键id',
    post_id              bigint  comment '岗位id',
    role_id              bigint  comment '角色id',
    primary key (id)
);

alter table sys_post_role comment '岗位角色表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
    id                   bigint not null  comment '主键id',
    role_name            varchar(100)  comment '角色名称',
    role_code            varchar(100)  comment '角色编码',
    role_type            int  comment '角色类型1超管2内部角色3自定义',
    remark               varchar(255)  comment '备注',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_role comment '角色表';

/*==============================================================*/
/* Table: sys_role_data_scope                                   */
/*==============================================================*/
create table sys_role_data_scope
(
    id                   bigint not null  comment '主键id',
    role_id              bigint  comment '角色id',
    menu_id              bigint  comment '菜单id',
    data_scope           int  comment '数据范围类型1所有2本公司3本公司及以下4本部门5本部门及以下6本人7本人及下属',
    primary key (id),
    unique key AK_Identifier_2 (role_id, menu_id)
);

alter table sys_role_data_scope comment '角色数据范围';

/*==============================================================*/
/* Table: sys_role_form_field                                   */
/*==============================================================*/
create table sys_role_form_field
(
    id                   bigint not null  comment '主键id',
    role_id              bigint  comment '角色id',
    form_id              bigint  comment '表单id',
    field_id             bigint  comment '字段id',
    per_type             int  comment '权限类型',
    condition_expression varchar(255)  comment '条件表达式',
    primary key (id)
);

alter table sys_role_form_field comment '角色表单字段权限表';

/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
    id                   bigint not null  comment '主键id',
    role_id              bigint  comment '角色id',
    per_id               bigint  comment '权限id',
    primary key (id)
);

alter table sys_role_permission comment '角色权限表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
    id                   bigint not null  comment '主键id',
    username             varchar(100)  comment '用户名',
    password             varchar(100)  comment '密码',
    phone                varchar(50)  comment '手机号',
    email                varchar(50)  comment '邮箱',
    real_name            varchar(100)  comment '真实姓名',
    per_version          int  comment '权限版本',
    org_id               bigint  comment '所属组织id',
    account_type         int  comment '账号类型1内部2外部',
    create_time          datetime  comment '创建时间',
    create_user_id       bigint  comment '创建人id',
    update_time          datetime  comment '更新时间',
    update_user_id       bigint  comment '更新人id',
    is_enable            int  comment '是否启用0否1是',
    is_del               int  comment '是否逻辑删除0否1是',
    primary key (id)
);

alter table sys_user comment '用户表';

/*==============================================================*/
/* Table: sys_user_post                                         */
/*==============================================================*/
create table sys_user_post
(
    id                   bigint not null  comment '主键id',
    user_id              bigint  comment '用户id',
    post_id              bigint  comment '岗位id',
    is_primary           int  comment '是否主岗位1是0否',
    primary key (id)
);

alter table sys_user_post comment '用户岗位表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
    id                   bigint not null  comment '',
    user_id              bigint  comment '',
    role_id              bigint  comment '',
    primary key (id)
);

alter table sys_user_role comment '用户角色表';
