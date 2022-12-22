package cn.yznu.modules.sys.vo;

import cn.yznu.common.utils.vo.BaseVo;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class RoleVo extends BaseVo {

    @ApiParam("角色名称")
    private String roleName;

}
