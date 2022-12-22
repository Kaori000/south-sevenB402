package cn.yznu.modules.sys.redis;

import cn.yznu.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 平台Redis
 *
 */
@Component
public class SysPlatformRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(String appId, Integer Type) {
        if(StringUtil.isEmpty(appId) || Type == null){
            return ;
        }
        String key = this.getSysPlatformKey(appId);
        redisUtils.set(key, String.valueOf(Type));
    }

    public Integer delete(String appId) {
        String key = this.getSysPlatformKey(appId);
        Integer type = getByKey(key);
        redisUtils.delete(key);
        return type;
    }

    public Integer get(String appId){
        String key = this.getSysPlatformKey(appId);
        return getByKey(key);
    }

    private Integer getByKey(String key){
        String value = redisUtils.get(key);
        if(StringUtil.isNotEmpty(value)){
            Integer type = Integer.parseInt(value);
            redisUtils.delete(key);
            return type;
        }else {
            redisUtils.delete(key);
            return null;
        }
    }

    public boolean exist(String appId){
        String key = this.getSysPlatformKey(appId);
        return redisUtils.exist(key);
    }

    private String getSysPlatformKey(String appId){
        return "sys:platform:" + appId;
    }
}
