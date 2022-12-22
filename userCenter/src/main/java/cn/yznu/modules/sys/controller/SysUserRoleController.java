package cn.yznu.modules.sys.controller;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.modules.sys.entity.SysUserRoleEntity;
import cn.yznu.modules.sys.service.SysUserRoleService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;
import cn.yznu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author lfh
 * @email kaoriii@163.com
 * @date 2021-11-10 14:25:33
 */
@RestController
@RequestMapping("/sys/sysuserrole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:sysuserrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:sysuserrole:info")
    public R info(@PathVariable("id") String id){
        SysUserRoleEntity sysUserRole = sysUserRoleService.getById(id);

        return R.ok().put("sysUserRole", sysUserRole);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:sysuserrole:save")
    public R save(@RequestBody SysUserRoleEntity sysUserRole){
        sysUserRoleService.save(sysUserRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:sysuserrole:update")
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
    @RequiresPermissions("sys:sysuserrole:delete")
    public R delete(@RequestBody String[] ids){
        sysUserRoleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
