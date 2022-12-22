package cn.yznu.modules.sys.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;
    @Resource(name="redisTemplate")
    private HashOperations<String, String, Object> hashOperations;
    @Resource(name="redisTemplate")
    private ListOperations<String, Object> listOperations;
    @Resource(name="redisTemplate")
    private SetOperations<String, Object> setOperations;
    @Resource(name="redisTemplate")
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 48;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public void setAlways(String key, Object value) {
        valueOperations.set(key, toJson(value));
    }

    public Set getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public Integer incr(String key) {
        if (!exist(key)) {
            set(key, "1", NOT_EXPIRE);
        } else {
            set(key, Integer.valueOf(get(key)) + 1, NOT_EXPIRE);
        }
        return Integer.valueOf(get(key));
    }

    public void rename(String key, String newKey) {
        String value = get(key);
        set(newKey, value, DEFAULT_EXPIRE);
        delete(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Object getHash(String key, String hkey) {
        return hashOperations.get(key, hkey);
    }

    public void setHash(String key, String hkey, Object value) {
        hashOperations.put(key, hkey, value);
    }

    /************* set ***************/
    /**
     * set 中存入数据
     * @param key
     * @param value
     * @return
     */
    public Long sadd(String key, Object value){
        return setOperations.add(key,toJson(value));
    }

    /**
     * 获取set 中总条数
     * @param key
     * @return
     */
    public Long scount(String key){
        return setOperations.size(key);
    }

    /**
     * 获取set中所有数据
     * @param key
     * @return
     */
    public Set<Object> getAll(String key){
        return setOperations.members(key);
    }
    /************* set end **************/
    public String getSessionByUserId(String userId) {
        return (String) getHash("seMapping", String.valueOf(userId));
    }


    public void setSessionByUserId(String userId, String sessionId) {
        setHash("seMapping", String.valueOf(userId), sessionId);
    }

    //用户id对应token
    public String getTokenByUserId(String userId) {
        return (String) getHash("user:id:token", userId);
    }

    public void setTokenByUserId(Long userId, String token) {
        setHash("user:id:token", String.valueOf(userId), token);
    }

    public String getDeviceCmdLog(String id){
        return (String) getHash("device:cmd:log", id);
    }

    public void setDeviceCmdLog(String id, String vo){
        setHash("device:cmd:log", id, vo);
    }

    public boolean exist(String key) {
        return redisTemplate.hasKey(key);
//        return valueOperations.getOperations().hasKey(key);
    }

    public boolean existHash(String key) {
        return hashOperations.getOperations().hasKey(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
}
