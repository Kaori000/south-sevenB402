package cn.yznu.modules.sys.service.impl;

import cn.yznu.common.utils.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.modules.sys.dao.SysUserDao;
import cn.yznu.modules.sys.entity.SysUserEntity;
import cn.yznu.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
        );

        return new PageUtils(page);
    }

}
