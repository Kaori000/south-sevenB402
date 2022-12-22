package cn.yznu.modules.sys.redis;


import cn.yznu.modules.sys.entity.SysDictEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 系统参数Redis
 *
 */
@Component
public class SysDictRedis {
    @Autowired
    private RedisUtils redisUtils;

    public Set getKeys(String pattern) {
        return redisUtils.getKeys(pattern);
    }

    public void saveOrUpdate(SysDictEntity dictEntity) {
        if(dictEntity == null){
            return ;
        }
        String key = RedisKeys.getSysDictKey(dictEntity.getType(), dictEntity.getCode());
        redisUtils.setAlways(key, dictEntity);
    }

    public void delete(String type, String code) {
        String key = RedisKeys.getSysDictKey(type, code);
        redisUtils.delete(key);
    }
    public void deleteByKey(String key) {
        redisUtils.delete(key);
    }

    public SysDictEntity get(String type, String code){
        String key = RedisKeys.getSysDictKey(type, code);
        return redisUtils.get(key, SysDictEntity.class);
    }

    public SysDictEntity get(String key){
        return redisUtils.get(key, SysDictEntity.class);
    }
}
