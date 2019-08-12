package com.xxm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SequenceUtil {

    //四位数，按顺序编号，不够的以0补足
    static final int DEFAULT_LENGTH=4;
    public static String getNumber (long num) {
           String strNum=String.valueOf(num);
           int len=strNum.length();
           if (len>DEFAULT_LENGTH){
               return strNum;
           }
           int time=DEFAULT_LENGTH-len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i <time;i++){
                sb.append('0');
           }
           sb.append(strNum);
           return sb.toString();
    }

    //获取当前日期格式化
    public static String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = dateFormat.format(new Date());
        return currentDate;
    }
}
