package cn.yznu.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.yznu.modules.sys.entity.SysFileListEntity;
import cn.yznu.modules.sys.service.SysFileListService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;



/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:54
 */
@RestController
@RequestMapping("sys/filelist")
public class SysFileListController {
    @Autowired
    private SysFileListService sysFileListService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:filelist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysFileListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:filelist:info")
    public R info(@PathVariable("id") String id){
        SysFileListEntity sysFileList = sysFileListService.getById(id);

        return R.ok().put("sysFileList", sysFileList);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:filelist:save")
    public R save(@RequestBody SysFileListEntity sysFileList){
        sysFileListService.save(sysFileList);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:filelist:update")
    public R update(@RequestBody SysFileListEntity sysFileList){
        ValidatorUtils.validateEntity(sysFileList);
        sysFileListService.updateById(sysFileList);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:filelist:delete")
    public R delete(@RequestBody String[] ids){
        sysFileListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
