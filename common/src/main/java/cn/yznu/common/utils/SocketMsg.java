package cn.yznu.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class SocketMsg {

    public SocketMsg() {
    }

    public SocketMsg(Integer type, Object data) {
        this.type = type;
        this.data = data;
    }

    private Integer type;

    private Object data;

    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }
}
