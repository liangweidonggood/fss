ALTER TABLE sys_user
    ADD COLUMN IF NOT EXISTS user_status SMALLINT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS pwd_error_count SMALLINT NOT NULL DEFAULT 0,
    ADD COLUMN IF NOT EXISTS account_expire_time TIMESTAMP NULL,
    ADD COLUMN IF NOT EXISTS credentials_expire_time TIMESTAMP NULL,
    ADD COLUMN IF NOT EXISTS locked_reason VARCHAR(255) DEFAULT ''::VARCHAR,
    ADD COLUMN IF NOT EXISTS locked_time TIMESTAMP NULL;
-- 新增字段注释
COMMENT ON COLUMN sys_user.user_status IS '用户状态 0正常 1禁用(人工) 2锁定(密码错误/风险)';
COMMENT ON COLUMN sys_user.pwd_error_count IS '密码错误次数';
COMMENT ON COLUMN sys_user.account_expire_time IS '账户过期时间（null=永不过期）';
COMMENT ON COLUMN sys_user.credentials_expire_time IS '密码过期时间（null=永不过期）';
COMMENT ON COLUMN sys_user.locked_reason IS '锁定原因（如：密码错误5次/异地登录）';
COMMENT ON COLUMN sys_user.locked_time IS '锁定时间（审计用）';
