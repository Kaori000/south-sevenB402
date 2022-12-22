package cn.yznu.modules.sys.service;

import cn.yznu.modules.sys.entity.SysFileListEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.yznu.common.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author lfh
 * @email kaoriii@163.com
 * @date 2021-11-04 21:16:05
 */
public interface SysFileListService extends IService<SysFileListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

