package cn.yznu.modules.sys.service.impl;

import cn.yznu.common.utils.Query;
import cn.yznu.modules.sys.dao.SysRoleDeptDao;
import cn.yznu.modules.sys.entity.SysRoleDeptEntity;
import cn.yznu.modules.sys.service.SysRoleDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yznu.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity> implements SysRoleDeptService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleDeptEntity> page = this.page(
                new Query<SysRoleDeptEntity>().getPage(params),
                new QueryWrapper<SysRoleDeptEntity>()
        );

        return new PageUtils(page);
    }

}
