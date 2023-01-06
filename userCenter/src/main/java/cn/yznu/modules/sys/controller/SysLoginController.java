package cn.yznu.modules.sys.controller;

import cn.yznu.common.utils.IPUtils;
import cn.yznu.common.utils.MessageCode;
import cn.yznu.common.utils.R;
import cn.yznu.common.utils.StringUtil;
import cn.yznu.modules.sys.entity.SysLoginLogEntity;
import cn.yznu.modules.sys.entity.SysUserEntity;
import cn.yznu.modules.sys.redis.RedisUtils;
import cn.yznu.modules.sys.service.SysFileListService;
import cn.yznu.modules.sys.service.SysLoginLogService;
import cn.yznu.modules.sys.shiro.ShiroUtils;
import cn.yznu.modules.sys.vo.LoginVo;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

/**
 * 登录相关
 */
@Api(tags = "登录相关模块")
@RestController
public class SysLoginController {
    @Autowired
    private Producer producer;

    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisOperationsSessionRepository redisOperationsSessionRepository;
    @Autowired
    private SysFileListService sysFileListService;

    @Value("${kaori.isSingle}")
    private boolean isSingle;



    @ApiIgnore
    @GetMapping("/base64Captcha")
    public R base64Captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        //response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", out);

        String base64bytes = Base64.getEncoder().encodeToString(out.toByteArray());
        return R.ok().put("captcha", "data:image/png;base64," + base64bytes)
                .put("session", Base64.getEncoder()
                        .encodeToString(ShiroUtils.getSubject()
                                .getSession()
                                .getId()
                                .toString()
                                .getBytes()
                        ));
    }


    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public R login(@RequestBody LoginVo loginObject, HttpServletRequest request) {
        String username = loginObject.getUsername();
        String password = loginObject.getPassword();
//        String captcha = loginObject.getCaptcha();
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return R.error(MessageCode.LOGIN_VERIFICATION_CODE_ERROR.getCode(), MessageCode.LOGIN_VERIFICATION_CODE_ERROR.getMsg());
//        }

        return loginMethod(username, password, "", request);
    }

    private R loginMethod(String username, String password, String captcha, HttpServletRequest request) {
        Subject subject = ShiroUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.error(MessageCode.LOGIN_USER_NOT_EXIT.getCode(), e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error(MessageCode.LOGIN_USER_NAME_OR_PASSWORD_INCORRECT.getCode(), MessageCode.LOGIN_USER_NAME_OR_PASSWORD_INCORRECT.getMsg());
        } catch (LockedAccountException e) {
            return R.error(MessageCode.LOGIN_UNER_ACCOUNT_FROZEN.getCode(), MessageCode.LOGIN_UNER_ACCOUNT_FROZEN.getMsg());
        } catch (AuthenticationException e) {
            return R.error(MessageCode.LOGIN_USER_ACCOUNT_VERIFICATION_ERROR.getCode(), MessageCode.LOGIN_USER_ACCOUNT_VERIFICATION_ERROR.getMsg());
        }

        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();


        //添加登录日志

        SysLoginLogEntity loginLogEntity = new SysLoginLogEntity();
        loginLogEntity.setLoginTime(new Date());
        loginLogEntity.setUsername(username);
        loginLogEntity.setIp(IPUtils.getIpAddr(request));
        sysLoginLogService.save(loginLogEntity);

        if (isSingle) {
            //查看是否之前有登录的删除掉
            String lastSessionId = redisUtils.getSessionByUserId(user.getId());
            if (StringUtil.isNotEmpty(lastSessionId)) {
                redisOperationsSessionRepository.deleteById(lastSessionId);
            }

            //设置session对应关系
            redisUtils.setSessionByUserId(user.getId(), ShiroUtils.getSubject().getSession().getId().toString());
        }
        return R.ok().put("user", user).put("session",Base64.getEncoder()
                .encodeToString(ShiroUtils.getSubject()
                        .getSession()
                        .getId()
                        .toString()
                        .getBytes()
                ));
    }

    @ApiOperation(value = "退出")
    @GetMapping(value = "logout")
    public R logout() {
        redisOperationsSessionRepository.deleteById(ShiroUtils.getSession().getId().toString());
        ShiroUtils.logout();
        return R.ok();
    }

}
