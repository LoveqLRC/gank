package rc.loveq.meizhi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Calendar;
import java.util.Date;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/2/2 18:00
 * Email:664215432@qq.com
 */

public class GankPagerAdapter extends FragmentPagerAdapter {

    private final Date date;

    public GankPagerAdapter(FragmentManager fm, Date date) {
        super(fm);
        this.date=date;
    }

    @Override
    public Fragment getItem(int position) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-position);

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
