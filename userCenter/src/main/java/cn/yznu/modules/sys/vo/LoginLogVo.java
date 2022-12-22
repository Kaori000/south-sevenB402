package cn.yznu.modules.sys.vo;

import cn.yznu.common.utils.vo.BaseVo;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class LoginLogVo extends BaseVo {

    @ApiParam("用户昵称")
    private String key;

}
