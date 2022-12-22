package cn.yznu.common.config;

import cn.yznu.common.utils.StaticUtils;
import com.alibaba.fastjson.JSONObject;
import cn.yznu.common.utils.R;
import cn.yznu.common.utils.StringUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShiroAuthFilterConfig extends FormAuthenticationFilter {

    public ShiroAuthFilterConfig() {
        super();
    }

    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     *
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String xToken = httpServletRequest.getHeader("X-Token");
        boolean isOut = false;
        if(StringUtil.isNotEmpty(xToken)){
            isOut = StaticUtils.getRedisSessionExpire(xToken);
        }
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JSONObject.toJSON(R.error(isOut?402:401, "Unauthorized")).toString());

        return false;
    }

}
