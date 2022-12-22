package cn.yznu.modules.sys.service.impl;

import cn.yznu.common.utils.Query;
import cn.yznu.modules.sys.dao.SysFileListDao;
import cn.yznu.modules.sys.entity.SysFileListEntity;
import cn.yznu.modules.sys.service.SysFileListService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yznu.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysFileListService")
public class SysFileListServiceImpl extends ServiceImpl<SysFileListDao, SysFileListEntity> implements SysFileListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysFileListEntity> page = this.page(
                new Query<SysFileListEntity>().getPage(params),
                new QueryWrapper<SysFileListEntity>()
        );

        return new PageUtils(page);
    }

}
