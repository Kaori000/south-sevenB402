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

import cn.yznu.modules.sys.entity.SysRoleMenuEntity;
import cn.yznu.modules.sys.service.SysRoleMenuService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;



/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:54
 */
@Api(tags = "角色菜单")
@RestController
@RequestMapping("sys/rolemenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:rolemenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:rolemenu:info")
    public R info(@PathVariable("id") String id){
        SysRoleMenuEntity sysRoleMenu = sysRoleMenuService.getById(id);

        return R.ok().put("sysRoleMenu", sysRoleMenu);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:rolemenu:save")
    public R save(@RequestBody SysRoleMenuEntity sysRoleMenu){
        sysRoleMenuService.save(sysRoleMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:rolemenu:update")
    public R update(@RequestBody SysRoleMenuEntity sysRoleMenu){
        ValidatorUtils.validateEntity(sysRoleMenu);
        sysRoleMenuService.updateById(sysRoleMenu);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:rolemenu:delete")
    public R delete(@RequestBody String[] ids){
        sysRoleMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
