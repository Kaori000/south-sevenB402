package cn.yznu.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CommonUtils {

    public static Map<String, Object> convertToMap(Object object) {
        Map<String,Object> map = null;
        try {
            map = new HashMap<String, Object>();

            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter!=null ? getter.invoke(object) : null;
                map.put(key, value);
            }
            //key 可能会把自己的class 和hashcode编进去，直接去掉
            map.remove("class");

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()){
            String key = it.next();
            if (map.get(key)==null || map.get(key)==""){
                map.remove(key);
                set = map.keySet();
                it = set.iterator();
            }
        }
        return map;
    }
}
