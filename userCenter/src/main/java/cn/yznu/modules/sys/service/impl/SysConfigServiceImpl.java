package cn.yznu.modules.sys.service.impl;

import cn.yznu.common.utils.Query;
import cn.yznu.modules.sys.dao.SysConfigDao;
import cn.yznu.modules.sys.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yznu.common.utils.PageUtils;
import cn.yznu.modules.sys.entity.SysConfigEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysConfigEntity> page = this.page(
                new Query<SysConfigEntity>().getPage(params),
                new QueryWrapper<SysConfigEntity>()
        );

        return new PageUtils(page);
    }

}
