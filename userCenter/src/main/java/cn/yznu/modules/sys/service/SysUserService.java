package cn.yznu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.modules.sys.entity.SysUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:54
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

