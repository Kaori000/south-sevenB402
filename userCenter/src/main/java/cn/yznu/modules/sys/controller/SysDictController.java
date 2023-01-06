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

import cn.yznu.modules.sys.entity.SysDictEntity;
import cn.yznu.modules.sys.service.SysDictService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;



/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:55
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") String id){
        SysDictEntity sysDict = sysDictService.getById(id);

        return R.ok().put("sysDict", sysDict);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDictEntity sysDict){
        sysDictService.save(sysDict);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDictEntity sysDict){
        ValidatorUtils.validateEntity(sysDict);
        sysDictService.updateById(sysDict);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody String[] ids){
        sysDictService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
