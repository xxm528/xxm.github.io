import com.xxm.util.SequenceUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class testDate {

    @Test
    public void testDate(){
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTime1 = localDateTime.minusDays(30);
       // System.out.println(localDateTime+"================="+localDateTime1);
        System.out.println(new Date().getTime());

       // System.out.println(SequenceUtil.getCurrentDate()+SequenceUtil.getNumber(111));
    }
}
