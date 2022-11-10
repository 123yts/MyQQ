package com.yts.myqq.util;

import com.alibaba.fastjson2.JSONObject;
import com.yts.myqq.model.Message;
import com.yts.myqq.model.User;

public class Parser {

    //解包用户信息
    public static User getUser(Object object){
        //User user = (User)object; 不行

        return JSONObject.parseObject(JSONObject.toJSONString(object), User.class);
    }

    //解包聊天信息
    public static Message getMessage(Object object){
        return JSONObject.parseObject(JSONObject.toJSONString(object), Message.class);
    }
}
