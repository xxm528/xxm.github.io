package com.xxm.controller;

import com.xxm.util.HttpClientUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;

@Controller
@RequestMapping("/xxm")
public class WxLoginController {

    //访问微信授权获取code和state(调用者访问时传入state就会有state返回，反之无)
    @GetMapping("/getCode")
    public void getCode() throws Exception {
        String redirect_uri="应填写的回调地址";
        String url = new StringBuilder().append("https://open.weixin.qq.com/connect/qrconnect?")
        .append("appid="+"appid").append("&response_type="+"code").append("&scope=snsapi_login")
        .append("&redirect_uri="+ URLEncoder.encode(redirect_uri,"utf-8")).toString();
        //此时获取返回的json串
        String str = HttpClientUtils.get(url,"gbk");
    }

    //微信用code回调第三方网站返回access_key
    @GetMapping("/getAccessKey")
    public ModelAndView callBack(String code,String state) throws Exception {
        if (!StringUtils.isEmpty(code)){
            String url = new StringBuilder().append("https://api.weixin.qq.com/sns/oauth2/access_token?")
                    .append("appid="+"appid").append("&secret="+"secret").append("&code="+code)
                    .append("&grant_type=authorization_code").toString();
            //此时获取返回的json串
            String str = HttpClientUtils.get(url,"utf-8");
        }
        return new ModelAndView("login");
    }
}
