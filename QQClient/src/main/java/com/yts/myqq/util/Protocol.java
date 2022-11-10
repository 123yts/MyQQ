package com.yts.myqq.util;

import com.alibaba.fastjson2.JSONObject;
import com.yts.myqq.constant.SystemConstant;

import java.util.HashMap;
import java.util.Map;

public class Protocol {

    public static String request(String msgType, Object object){

        Map<String, Object> map = new HashMap();
        map.put(SystemConstant.MSG_TYPE, msgType);
        map.put(SystemConstant.DATA, object);
        //System.out.println("protocol: " + object);
        return JSONObject.toJSONString(map);
    }

}
