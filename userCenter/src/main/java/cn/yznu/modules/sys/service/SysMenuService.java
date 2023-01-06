package cn.yznu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 *
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:54
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysMenuEntity> getMenuList();

    List<SysMenuEntity> getMenu();
}

