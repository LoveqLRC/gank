package rc.loveq.meizhi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 22:52
 * Email:664215432@qq.com
 */

public class Dates {
    public static boolean isTheSameDate(Date one, Date two){
        Calendar _one = Calendar.getInstance();
        _one.setTime(one);
        Calendar _two = Calendar.getInstance();
        _two.setTime(two);
        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
        int twoDay = _two.get(Calendar.DAY_OF_YEAR);
        return oneDay==twoDay;
    }

    public static String toDate(Date date) {
        java.text.DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }
}
