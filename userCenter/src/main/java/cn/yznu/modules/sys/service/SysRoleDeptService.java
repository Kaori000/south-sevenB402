package cn.yznu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.modules.sys.entity.SysRoleDeptEntity;

import java.util.Map;

/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:54
 */
public interface SysRoleDeptService extends IService<SysRoleDeptEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

