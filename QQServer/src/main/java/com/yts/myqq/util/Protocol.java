package com.yts.myqq.util;

import com.alibaba.fastjson2.JSONObject;
import com.yts.myqq.constant.SystemConstant;

import java.util.HashMap;
import java.util.Map;

public class Protocol {

    public static String retData(String msgType, String retCode, Object obj){
        Map<String, Object> map = new HashMap<>();
        map.put(SystemConstant.RET_CODE, retCode);
        map.put(SystemConstant.MSG_TYPE, msgType);
        map.put(SystemConstant.RET_DATA, obj);
        return JSONObject.toJSONString(map);
    }

}
