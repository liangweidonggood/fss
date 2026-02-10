package com.lwd.admin.core.util;

import com.lwd.admin.util.FlexCodegen;
import com.lwd.admin.util.FlexCodegenData;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


/**
 * Core 模块代码生成
 *
 * @author lwd
 */
@Slf4j
public class CoreCodegen {
    /**
     * 运行该方法生成代码.
     */
    public static void main(String[] args) {
        FlexCodegenData data = FlexCodegenData
                .builder()
                .jdbcUrl("jdbc:postgresql://localhost:5432/postgres?currentSchema=fss_master&useInformationSchema=true")
                .jdbcUsername("postgres")
                .jdbcPassword("1234")
                .projectModulePath("D:/workspace/github/fss/backend/admin-java/admin-core/admin-core-service")
                .basePackage("com.lwd.admin.core.system")
                .tablePrefix("sys_")
                .schema("fss_master")
                .tableNames(Set.of("sys_user"))
                .build()
                ;
        FlexCodegen.generate(data);
    }

}
