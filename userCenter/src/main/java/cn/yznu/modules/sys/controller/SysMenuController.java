package cn.yznu.modules.sys.controller;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.modules.sys.entity.SysMenuEntity;
import cn.yznu.modules.sys.service.SysMenuService;
import cn.yznu.common.utils.R;
import cn.yznu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author lfh
 * @email kaoriii@163.com
 * @date 2021-11-04 21:16:05
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    //@RequiresPermissions("sys:sysmenu:list")
    public R list(@RequestParam Map<String, Object> params){
        List<SysMenuEntity> allMenu = sysMenuService.getMenuList();

        return R.ok().put("data", allMenu);
    }

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/nav")
    //@RequiresPermissions("sys:sysmenu:list")
    public R nav(@RequestParam Map<String, Object> params){
        List<SysMenuEntity> allMenu = sysMenuService.getMenu();


        return R.ok().put("menu", allMenu);
    }

    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:sysmenu:info")
    public R info(@PathVariable("id") String id){
        SysMenuEntity sysMenu = sysMenuService.getById(id);

        return R.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:sysmenu:save")
    public R save(@RequestBody SysMenuEntity sysMenu){
        sysMenuService.save(sysMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:sysmenu:update")
    public R update(@RequestBody SysMenuEntity sysMenu){
        ValidatorUtils.validateEntity(sysMenu);
        sysMenuService.updateById(sysMenu);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:sysmenu:delete")
    public R delete(@RequestBody String[] ids){
        sysMenuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
