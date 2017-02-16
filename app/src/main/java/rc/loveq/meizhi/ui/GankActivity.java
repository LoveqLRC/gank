package rc.loveq.meizhi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Date;

import rc.loveq.meizhi.R;
import rc.loveq.meizhi.ui.base.ToolBarActivity;
import rc.loveq.meizhi.util.Dates;

public class GankActivity extends ToolBarActivity {

    public static final String EXTRA_DATE = "extra_date";
    private static final String TAG = "GankActivity";
    public Date mExtraDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       mExtraDate.toString()  Mon Jan 23 11:35:32 GMT+08:00 2017
//      Dates.toDate(mExtraDate)    2017/01/23
        mExtraDate = ((Date) getIntent().getSerializableExtra(EXTRA_DATE));
        String toDate = Dates.toDate(mExtraDate);
        setTitle(toDate);
        initViewPager();
        initTabLayout();
    }

    private void initTabLayout() {
    }

    private void initViewPager() {

    }
}
