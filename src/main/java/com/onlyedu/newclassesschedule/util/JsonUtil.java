package com.onlyedu.newclassesschedule.util;

import com.alibaba.fastjson.JSON;
/**
 * @author Andy
 * @date 2018/11/22 13:10
 */
public class JsonUtil {


    public static <T> T json2Object(String json,Class<T> clazz){
        return JSON.parseObject(json,clazz);
    }

    public static String object2Jon(Object object){
        return JSON.toJSONString(object);
    }
}
