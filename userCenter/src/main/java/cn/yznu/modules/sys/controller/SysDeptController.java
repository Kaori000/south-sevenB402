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

import cn.yznu.modules.sys.entity.SysDeptEntity;
import cn.yznu.modules.sys.service.SysDeptService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.common.utils.R;



/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:55
 */
@Api(tags = "部门")
@RestController
@RequestMapping("sys/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @SysLog("列表")
    @GetMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDeptService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @SysLog("信息")
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dept:info")
    public R info(@PathVariable("id") String id){
        SysDeptEntity sysDept = sysDeptService.getById(id);

        return R.ok().put("sysDept", sysDept);
    }

    /**
     * 保存
     */
    @SysLog("保存")
    @PostMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public R save(@RequestBody SysDeptEntity sysDept){
        sysDeptService.save(sysDept);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改")
    @PatchMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public R update(@RequestBody SysDeptEntity sysDept){
        ValidatorUtils.validateEntity(sysDept);
        sysDeptService.updateById(sysDept);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public R delete(@RequestBody String[] ids){
        sysDeptService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
