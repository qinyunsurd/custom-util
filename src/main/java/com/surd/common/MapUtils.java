package com.surd.common;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

/**
 * @author qinyunsurd
 * @date 2022年7月13日17:10:35
 */
public class MapUtils {
    private MapUtils() {
    }

    /**
     * 将object转为map集合
     *
     * @param entity
     * @return map
     * @throws IllegalAccessException
     */
    public static Map<String, Object> beanToMap(Object entity) throws IllegalAccessException {
        if (null == entity) {
            return Collections.emptyMap();
        }
        Map<String, Object> parameter = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object o = field.get(entity);
            parameter.put(field.getName(), o);
        }
        return parameter;
    }

    /**
     * 将map转换成为对象，有可能抛出一些异常，需要调用者自行处理
     * @param map 数据源
     * @param cls 指定的对象类字节
     * @return object
     * @param <T>
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ParseException
     */
    public static <T> T MapToObject(Map<String, Object> map, Class<T> cls) throws InstantiationException, IllegalAccessException, ParseException {
        Field[] fields = cls.getDeclaredFields();
        T t = null;
        if (fields.length > 0) {
            t = cls.newInstance();
        }

        boolean flag;
        for (Field field : fields) {
            if (map.containsKey(field.getName()) && map.get(field.getName()) != null) {
                flag = false;
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                    flag = true;
                }

                field.set(t, map.get(field.getName()));

                if (flag) {
                    field.setAccessible(false);
                }
            }
        }
        return t;
    }

    /**
     * 将map转换成url,如果值为空，则不进行处理
     *
     * @param map parameter
     * @return string, like this: a=1&b=2
     */
    public static String convertMapToUrl(Map<String, String> map) {
        if (null == map || map.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            if (null != value && !"".equals(value)) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    /**
     * @param map    数据源
     * @param value  value
     * @param islike 是否模糊查找，TRUE是 FALSE否
     * @return value对应的key
     */
    public static String getKeyByValue(Map map, String value, boolean islike) {
        Set set = map.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (islike) {
                if (String.valueOf(entry.getValue()).contains(value)) {
                    return entry.getKey().toString();
                }
            } else {
                if (entry.getValue().equals(value)) return entry.getKey().toString();
            }
        }
        return "";
    }
}
