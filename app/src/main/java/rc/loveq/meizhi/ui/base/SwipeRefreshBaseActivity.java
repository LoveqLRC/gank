package rc.loveq.meizhi.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.widget.MultiSwipeRefreshLayout;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 16:35
 * Email:664215432@qq.com
 */

public abstract class SwipeRefreshBaseActivity extends ToolBarActivity implements SwipeRefreshLayer {
    @BindView(R.id.swipe_refresh_layout)
    public MultiSwipeRefreshLayout mSwipeRefreshLayout;
    //是否正在刷新数据
    private boolean mIsRequestDataRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefresh();
    }


    private void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                    R.color.refresh_progress_2, R.color.refresh_progress_1);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });
        }
    }

    @Override
    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }

    @Override
    public void setRefresh(boolean refresh) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (refresh) {
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mIsRequestDataRefresh = false;
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        }
    }

    @Override
    public void setProgressViewOffset(boolean scale, int start, int end) {
        mSwipeRefreshLayout.setProgressViewOffset(scale,start,end);
    }

    @Override
    public void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback) {
        mSwipeRefreshLayout.setCanChildScrollUpCallback(callback);
    }


    public boolean isRequestDataRefresh() {
        return mIsRequestDataRefresh;
    }
}
