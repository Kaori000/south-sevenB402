package cn.yznu.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.common.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.yznu.modules.sys.entity.SysUserRoleEntity;
import cn.yznu.modules.sys.service.SysUserRoleService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;



/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:53
 */
@Api(tags = "用户角色")
@RestController
@RequestMapping("sys/userrole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:userrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:userrole:info")
    public R info(@PathVariable("id") String id){
        SysUserRoleEntity sysUserRole = sysUserRoleService.getById(id);

        return R.ok().put("sysUserRole", sysUserRole);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:userrole:save")
    public R save(@RequestBody SysUserRoleEntity sysUserRole){
        sysUserRoleService.save(sysUserRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:userrole:update")
    public R update(@RequestBody SysUserRoleEntity sysUserRole){
        ValidatorUtils.validateEntity(sysUserRole);
        sysUserRoleService.updateById(sysUserRole);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:userrole:delete")
    public R delete(@RequestBody String[] ids){
        sysUserRoleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
