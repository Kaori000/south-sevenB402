package cn.yznu.modules.sys.controller;

import cn.yznu.common.annotation.SysLog;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;
import cn.yznu.common.validator.ValidatorUtils;
import cn.yznu.modules.sys.entity.SysFileListEntity;
import cn.yznu.modules.sys.service.SysFileListService;
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
@RequestMapping("/sys/sysfilelist")
public class SysFileListController {
    @Autowired
    private SysFileListService sysFileListService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:sysfilelist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysFileListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:sysfilelist:info")
    public R info(@PathVariable("id") String id){
        SysFileListEntity sysFileList = sysFileListService.getById(id);

        return R.ok().put("sysFileList", sysFileList);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:sysfilelist:save")
    public R save(@RequestBody SysFileListEntity sysFileList){
        sysFileListService.save(sysFileList);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:sysfilelist:update")
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
    @RequiresPermissions("sys:sysfilelist:delete")
    public R delete(@RequestBody String[] ids){
        sysFileListService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
