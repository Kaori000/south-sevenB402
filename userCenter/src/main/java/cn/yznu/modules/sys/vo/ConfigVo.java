package cn.yznu.modules.sys.vo;

import cn.yznu.common.utils.vo.BaseVo;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class ConfigVo extends BaseVo {

    @ApiParam("参数名")
    private String paramKey;
}
