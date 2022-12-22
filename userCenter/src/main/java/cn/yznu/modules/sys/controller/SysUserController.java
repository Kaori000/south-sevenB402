package cn.yznu.modules.sys.controller;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;
import cn.yznu.common.validator.ValidatorUtils;
import cn.yznu.modules.sys.entity.SysUserEntity;
import cn.yznu.modules.sys.service.SysUserService;
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
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:sysuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:sysuser:info")
    public R info(@PathVariable("id") String id){
        SysUserEntity sysUser = sysUserService.getById(id);

        return R.ok().put("sysUser", sysUser);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:sysuser:save")
    public R save(@RequestBody SysUserEntity sysUser){
        sysUserService.save(sysUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:sysuser:update")
    public R update(@RequestBody SysUserEntity sysUser){
        ValidatorUtils.validateEntity(sysUser);
        sysUserService.updateById(sysUser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:sysuser:delete")
    public R delete(@RequestBody String[] ids){
        sysUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
