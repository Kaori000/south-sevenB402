package cn.yznu.modules.sys.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class LoginVo {
    @ApiParam("用户名")
    private String username;
    @ApiParam("密码")
    private String password;
    @ApiParam("验证码")
    private String captcha;


}
