package cn.yznu.common.utils;

import cn.yznu.modules.sys.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
public class StaticUtils {

    private static StaticUtils staticUtils;

    @Autowired
    private RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        staticUtils = this;
        staticUtils.redisUtils = this.redisUtils;   // 初使化时将已静态化的service实例化
    }

    //之前登陆，被挤掉
    public static boolean getRedisSessionExpire(String xToken){
        return staticUtils.redisUtils.existHash("spring:session:sessions:" + new String(Base64.getDecoder().decode(xToken)));
    }

    //已经没有权限
    public static boolean getRedisExpire(String xToken){
        return staticUtils.redisUtils.existHash("spring:session:sessions:expires:" + new String(Base64.getDecoder().decode(xToken)));
    }
}
