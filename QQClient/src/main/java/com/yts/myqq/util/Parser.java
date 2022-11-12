package com.yts.myqq.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yts.myqq.constant.SystemConstant;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;

import java.util.List;
import java.util.Map;

public class Parser {

    public static User getRetLogin(String response){
        Map map = JSONObject.parseObject(response);
        //验证失败, 返回null
        if (!SystemConstant.SUCCESS.equals(map.get(SystemConstant.RET_CODE))){
            return null;
        }
        else {
            return JSON.parseObject(JSONObject.toJSONString(map.get(SystemConstant.RET_DATA)), User.class);
        }
    }

    public static boolean getRetRegister(String response){
        Map map = JSONObject.parseObject(response);
        return SystemConstant.SUCCESS.equals(map.get(SystemConstant.RET_CODE));
    }

    public static Message getRetMessage(String response){
        //解包聊天信息
        Map map = JSONObject.parseObject(response);
        return JSONObject.parseObject(JSONObject.toJSONString(map.get(SystemConstant.RET_DATA)), Message.class);

    }

    public static List<User> getRetFriendList(String response){
        Map map = JSONObject.parseObject(response);
        if (map.get(SystemConstant.RET_DATA) == null) return null;
        List<User> friendList = JSON.parseArray(JSONObject.toJSONString(map.get(SystemConstant.RET_DATA)), User.class);
        return friendList;
    }

}
