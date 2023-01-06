package cn.yznu.modules.sys.dao;

import cn.yznu.modules.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author samui
 * @email kaoriii@163.com
 * @date 2023-01-06 16:19:54
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {
    @Select("select * from sys_menu")
    public List<SysMenuEntity> getAllMenu();
}
