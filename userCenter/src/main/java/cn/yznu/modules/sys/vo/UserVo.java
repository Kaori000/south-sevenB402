package cn.yznu.modules.sys.vo;

import cn.yznu.common.utils.vo.BaseVo;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UserVo extends BaseVo {

    @ApiParam("部门id")
    private String deptId;

    @ApiParam("用户名称")
    private String username;

    @ApiParam("状态")
    private String status;
}
