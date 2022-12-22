package cn.yznu.modules.sys.vo;

import cn.yznu.common.utils.vo.BaseVo;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class DictVo extends BaseVo {

    @ApiParam("字典名称")
    private String name;
}
