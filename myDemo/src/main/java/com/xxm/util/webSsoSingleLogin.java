package com.xxm.util;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//session监听
public class webSsoSingleLogin implements HttpSessionListener{

    //保存sessionID-username的映射(容器)
    private static HashMap hUserName = new HashMap();

    //session监听内部提供的方法
    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }
    //session销毁时-移除sessionId
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        hUserName.remove(se.getSession().getId());
    }

    //提供判断用户是否已经登陆的方法 --(sessionId和username)
    public static boolean isAlreadyLogin(HttpSession session,String userName){
        boolean flag=false;
        //判断用户是否登陆 -- 使上次登陆的用户掉线
       if (hUserName.containsValue(userName)){
           flag=true;
           //删除上次的sessionId-userName;
           Iterator iterator = hUserName.entrySet().iterator();
           while (iterator.hasNext()){
               Map.Entry entry = (Map.Entry)iterator.next();
               String key= (String) entry.getKey();
               String value = (String) entry.getValue();
               if (value.equals(userName)){
                   hUserName.remove(key);
               }
           }
           //添加现在的sessionId-username
           hUserName.put(session.getId(),userName);
           System.out.println("=============当前用户名 username:"+userName);
       }else {
           flag=false;
           //未登录的直接添加
           hUserName.put(session.getId(),userName);
           System.out.println("=============当前用户名 username:"+userName);
       }
        return flag;
    }

    //判断用户是否在线
    public static boolean isOnline(HttpSession session){
        boolean flag=false;
        if (hUserName.containsKey(session.getId())){
            flag=true;
        }else {
            flag=false;
        }
        return flag;
    }
}
