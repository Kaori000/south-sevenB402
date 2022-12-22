package cn.yznu.modules.sys.controller;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;
import cn.yznu.common.validator.ValidatorUtils;
import cn.yznu.modules.sys.entity.SysRoleEntity;
import cn.yznu.modules.sys.service.SysRoleService;
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
 * @date 2021-11-04 21:16:05
 */
@RestController
@RequestMapping("/sys/sysrole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:sysrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:sysrole:info")
    public R info(@PathVariable("id") String id){
        SysRoleEntity sysRole = sysRoleService.getById(id);

        return R.ok().put("sysRole", sysRole);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:sysrole:save")
    public R save(@RequestBody SysRoleEntity sysRole){
        sysRoleService.save(sysRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:sysrole:update")
    public R update(@RequestBody SysRoleEntity sysRole){
        ValidatorUtils.validateEntity(sysRole);
        sysRoleService.updateById(sysRole);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:sysrole:delete")
    public R delete(@RequestBody String[] ids){
        sysRoleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
