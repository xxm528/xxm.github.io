import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import java.util.ArrayList;

public class testarray {

    @Test
    public void test(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);

        String string = JSON.toJSONString(integers);
        System.out.println(string);

        JSONArray objects = JSONArray.parseArray("[1,2,3]");
        for (Object integer:objects){
            System.out.println(Integer.parseInt(String.valueOf(integer)));
        }
    }
}
