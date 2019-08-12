package com.xxm.controller;

import com.xxm.entity.UserInfo;
import com.xxm.service.UserService;
import com.xxm.util.RedisService;
import com.xxm.util.SequenceUtil;
import com.xxm.util.exportExcelUtil;
import com.xxm.util.getIpUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/xxm")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;

    @Resource
    private RedisService redisService;

    //页面部署存在问题，暂不使用
    @GetMapping("/index")
    public String show (Model model){
        List<UserInfo> userInfoList = userService.getUsers();
        model.addAttribute("users",userInfoList);
        return "index";
    }

    //获取ip及地理位置
    @GetMapping("/getIp")
    @ResponseBody
    public String getIp (HttpServletRequest request){
        String ip=getIpUtil.getIp(request);
        System.out.println("ip==========="+ip);
        String address="";
        try {
            address= getIpUtil.getIpAddress(ip,"gbk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    //增加代码导出功能
    @GetMapping("/export")
    @ResponseBody
    public String export (HttpServletResponse response){
        List<UserInfo> userInfoList = userService.getUsers();
        //model.addAttribute("users",userInfoList);

        // excel标题
        String[] title = {"编号", "姓名", "密码"};
        // excel文件名
        String fileName = "用户订单信息统计表" + System.currentTimeMillis() + ".xls";
        // sheet名
        String sheetName = "用户统计表";
        String[][] content = new String[userInfoList.size()][title.length];
        for (int i = 0; i < userInfoList.size(); i++) {
            UserInfo user=userInfoList.get(i);
            content[i][0] = user.getId()+ "";
            content[i][1] = user.getUserName() + "";
            content[i][2] = user.getUserPassword();
        }
        //合并单元格的内容  ---暂不使用
        String mergeCell = "";
        Integer cellNum = 0;//合并单元格几列
        // 创建HSSFWorkbook
        HSSFWorkbook wb = exportExcelUtil.getHSSFWorkbook(sheetName, title, content, null,mergeCell,cellNum);
        // 响应到客户端
        try {
            fileName = new String(fileName.getBytes(), "ISO8859-1"); //指定编码防止乱码
            this.setResponseHeader(response,fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    private void setResponseHeader(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);  //设置浏览器弹出下载框
        response.addHeader("Pargam", "no-cache");  //设置不要将网页存于缓存之中
        response.addHeader("Cache-Control", "no-cache");
    }

    @GetMapping("/testIncr")
    @ResponseBody
    public String test (Model model){
        String currentDate = SequenceUtil.getCurrentDate();
        //进行赋值操作
        redisService.setValue(currentDate);
        //取值操作
        Integer value = redisTemplate.opsForValue().get(currentDate);
        String number = SequenceUtil.getNumber(value);
        String maintainNum=currentDate+number;
        System.out.println("生命周期为=========="+redisService.getKeyExpire(currentDate, TimeUnit.DAYS));
        return maintainNum;
        }
}
