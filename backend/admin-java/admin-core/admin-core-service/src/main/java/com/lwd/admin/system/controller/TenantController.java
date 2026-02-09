package com.lwd.admin.system.controller;

import com.lwd.admin.system.entity.Tenant;
import com.lwd.admin.system.service.TenantService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户表 控制层。
 *
 * @author lwd
 * @since 2026-02-06
 */
@RequiredArgsConstructor
@Tag(name = "租户管理", description = "租户管理")
@RestController
@RequestMapping("/tenant")
public class TenantController {


    private final TenantService tenantService;

    /**
     * 保存租户表。
     *
     * @param tenant 租户表
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @Operation(summary = "保存")
    @PostMapping("save")
    public boolean save(@RequestBody Tenant tenant) {
        return tenantService.save(tenant);
    }

    /**
     * 根据主键删除租户表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "删除")
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return tenantService.removeById(id);
    }

    /**
     * 根据主键更新租户表。
     *
     * @param tenant 租户表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "更新")
    @PutMapping("update")
    public boolean update(@RequestBody Tenant tenant) {
        return tenantService.updateById(tenant);
    }

    /**
     * 查询所有租户表。
     *
     * @return 所有数据
     */
    @Operation(summary = "查所有")
    @GetMapping("list")
    public List<Tenant> list() {
        return tenantService.list();
    }

    /**
     * 根据主键获取租户表。
     *
     * @param id 租户表主键
     * @return 租户表详情
     */
    @Operation(summary = "查单个")
    @GetMapping("getInfo/{id}")
    public Tenant getInfo(@PathVariable Long id) {
        return tenantService.getById(id);
    }

    /**
     * 分页查询租户表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @GetMapping("page")
    public Page<Tenant> page(Page<Tenant> page) {
        return tenantService.page(page);
    }

}
