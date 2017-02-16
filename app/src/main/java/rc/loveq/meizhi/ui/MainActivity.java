package rc.loveq.meizhi.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import rc.loveq.meizhi.App;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.data.MeiZhiData;
import rc.loveq.meizhi.data.RestVideoData;
import rc.loveq.meizhi.data.entity.Gank;
import rc.loveq.meizhi.data.entity.MeiZhi;
import rc.loveq.meizhi.event.OnMeizhiTouchListener;
import rc.loveq.meizhi.ui.adapter.MeiZhiAdapter;
import rc.loveq.meizhi.ui.base.SwipeRefreshBaseActivity;
import rc.loveq.meizhi.util.Dates;
import rc.loveq.meizhi.util.Once;
import rc.loveq.meizhi.util.PreferencesLoader;
import rc.loveq.meizhi.util.Toasts;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;


@RuntimePermissions
public class MainActivity extends SwipeRefreshBaseActivity {
    //预加载个数
    private static final int PRELOAD_SIZE = 6;
    public static final String TAG = "MainActivity";
    public List<MeiZhi> mMeiZhiList;
    public MeiZhiAdapter mMeiZhiAdapter;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    private boolean mIsFirstTouchBottom = true;
    private int mPage = 1;
    private int mLastVideoIndex;
    private boolean mMeizhiBeTouch;

    @Override
    public void onToolBarClick() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeiZhiList = new ArrayList<>();
        QueryBuilder queryBuilder = new QueryBuilder(MeiZhi.class);
        queryBuilder.appendOrderDescBy("publishedAt");
        queryBuilder.limit(0, 10);
        ArrayList<MeiZhi> mlist = App.sDb.query(queryBuilder);
        mMeiZhiList.addAll(mlist);
        setupRecyclerView();
        MainActivityPermissionsDispatcher.requestPermissionCallBackWithCheck(this);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefresh(true);

            }
        }, 356);
        if (mRecyclerView != null) {
            loadData(true);
        } else {
            Toasts.showShort("mRecyclerView == null");
        }
    }

    /**
     * 给RecyclerView设置LayoutManager
     * 绑定Adapter
     * 设置滚动监听
     * 设置Item点击事件
     */
    private void setupRecyclerView() {
        if (mRecyclerView == null) {
            return;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mMeiZhiAdapter = new MeiZhiAdapter(this, mMeiZhiList);
        mRecyclerView.setAdapter(mMeiZhiAdapter);
        new Once(this).show("tip_guide_6", new Once.OnceCallback() {
            @Override
            public void onOnce() {
                Snackbar.make(mRecyclerView, R.string.tip_guide, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.i_konw, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();

            }
        });
        mRecyclerView.addOnScrollListener(getOnBottomListener(staggeredGridLayoutManager));
        mMeiZhiAdapter.setOnMeizhiTouchListener(getOnMeizhiTouchListener());
    }

    private OnMeizhiTouchListener getOnMeizhiTouchListener() {
        return new OnMeizhiTouchListener() {
            @Override
            public void onTouch(View v, final View meizhiView, View card, final MeiZhi meizhi) {
                if (meizhi == null) {
                    return;
                }

                if (v == meizhiView && !mMeizhiBeTouch) {
                    mMeizhiBeTouch = true;
                    Picasso.with(MainActivity.this).load(meizhi.url).fetch(new Callback() {
                        @Override
                        public void onSuccess() {
                            mMeizhiBeTouch = false;
                            startPictureActivity(meizhi, meizhiView);
                        }

                        @Override
                        public void onError() {
                            mMeizhiBeTouch = false;
                        }
                    });
                }

                if (v == card) {
                    startGankActivity(meizhi.publishedAt);
                }
            }
        };
    }

    private void startGankActivity(Date publishedAt) {
        Intent intent=new Intent(this,WebDetailActivity.class);
//        intent.putExtra(GankActivity.EXTRA_DATE,publishedAt);
        startActivity(intent);
    }


    private void startPictureActivity(MeiZhi meizhi, View meizhiView) {
        Intent intent = PictureActivity.newIntent(MainActivity.this, meizhi.url, meizhi.desc);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this, meizhiView, PictureActivity.TRANSITION_PIC);
        ActivityCompat.startActivity(MainActivity.this, intent, optionsCompat.toBundle());
    }

    private RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager manager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isBottom = manager.findLastVisibleItemPositions(new int[2])[1]
                        > mMeiZhiAdapter.getItemCount() - PRELOAD_SIZE;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTouchBottom) {
                        Log.d(TAG, "onScrolled:加载下一页 ");
                        mSwipeRefreshLayout.setRefreshing(true);
                        mPage += 1;//加载下一页
                        loadData();
                    } else {
                        Log.d(TAG, "第一次到达底部");
                        mIsFirstTouchBottom = false;
                    }
                }
            }
        };
    }

    private void loadData() {
        loadData(false);
    }

    private void loadData(final boolean clean) {
        mLastVideoIndex = 0;
        Subscription subscription = Observable.zip(sGankIO.getMeiZhiData(mPage),
                sGankIO.getRestVideoData(mPage),
                new Func2<MeiZhiData, RestVideoData, MeiZhiData>() {
                    @Override
                    public MeiZhiData call(MeiZhiData meizhidata, RestVideoData restdata) {
                        Log.d(TAG, "call: mPage" + mPage);
                        Log.d(TAG, "call: " + meizhidata);
                        return createMeiZhiDataWithRestVideoData(meizhidata, restdata);
                    }
                }
        ).map(new Func1<MeiZhiData, List<MeiZhi>>() {
            @Override
            public List<MeiZhi> call(MeiZhiData data) {
                return data.results;
            }
        }).flatMap(new Func1<List<MeiZhi>, Observable<MeiZhi>>() {
            @Override
            public Observable<MeiZhi> call(List<MeiZhi> zhis) {
                return Observable.from(zhis);
            }
        }).toSortedList(new Func2<MeiZhi, MeiZhi, Integer>() {
            @Override
            public Integer call(MeiZhi zhi, MeiZhi zhi2) {
                return zhi2.publishedAt.compareTo(zhi.publishedAt);
            }
        }).doOnNext(new Action1<List<MeiZhi>>() {
            @Override
            public void call(List<MeiZhi> zhis) {
                saveMeiZhi(zhis);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .finallyDo(new Action0() {
                    @Override
                    public void call() {
                        MainActivity.this.setRefresh(false);
                    }
                })
                .subscribe(new Action1<List<MeiZhi>>() {
                    @Override
                    public void call(List<MeiZhi> zhis) {
                        if (clean) {
                            mMeiZhiList.clear();
                        }
                        mMeiZhiList.addAll(zhis);
                        mMeiZhiAdapter.notifyDataSetChanged();
                        MainActivity.this.setRefresh(false);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        MainActivity.this.loadError(throwable);
                    }
                });
        addSubscription(subscription);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Snackbar.make(mRecyclerView, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestDataRefresh();
                    }
                }).show();
    }

    /**
     * 下拉刷新的时候回调
     */
    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        mPage = 1;
        loadData(true);
    }

    /**
     * 保存数据到数据库中
     *
     * @param meiZhiList
     */
    private void saveMeiZhi(List<MeiZhi> meiZhiList) {
        App.sDb.insert(meiZhiList, ConflictAlgorithm.Replace);
    }

    private MeiZhiData createMeiZhiDataWithRestVideoData(MeiZhiData meizhidata, RestVideoData restdata) {
        for (MeiZhi result : meizhidata.results) {
            result.desc = result.desc + " "
                    + getFirstVideoDesc(result.publishedAt, restdata.results);
        }
        return meizhidata;

    }

    private String getFirstVideoDesc(Date publishedAt, List<Gank> ganks) {
        String videoDesc = "";
        for (int i = mLastVideoIndex; i < ganks.size(); i++) {
            Gank gank = ganks.get(i);
            if (gank.publishedAt == null) gank.publishedAt = gank.createdAt;
            if (Dates.isTheSameDate(publishedAt, gank.publishedAt)) {
                videoDesc = gank.desc;
                mLastVideoIndex = i;
                break;
            }
        }
        return videoDesc;
    }

    ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_notifiable);
        initNotifiableItemState(item);
        return true;
    }

    /**
     * 初始化通知按钮状态
     *
     * @param item
     */
    private void initNotifiableItemState(MenuItem item) {
        PreferencesLoader preferencesLoader = new PreferencesLoader(this);
        item.setChecked(preferencesLoader.getBoolean(R.string.action_notifiable, false));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_trending://热门
                openGitHubTrending();
                return true;
            case R.id.action_notifiable://提醒
                boolean checked = item.isChecked();
                item.setChecked(!checked);
                PreferencesLoader loader = new PreferencesLoader(this);
                loader.saveBoolean(R.string.action_notifiable, !checked);
                Toasts.showShort(!checked ? R.string.notifiable_on : R.string.notifiable_off);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void openGitHubTrending() {
        String url = getString(R.string.url_github_trending);
        String title = getString(R.string.action_github_trending);
        Intent intent = WebActivity.newIntent(this, url, title);
        startActivity(intent);
    }

    @OnClick(R.id.main_fab)
    public void onFab(View v) {
        //TODO:onFab
        Toasts.showShort("onFab");
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermissionCallBack() {
//        Toast.makeText(this, , Toast.LENGTH_LONG).show();
        new Once(this).show("权限仅用于友盟统计及图片下载，请放心", new Once.OnceCallback() {
            @Override
            public void onOnce() {

            }
        });
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissionDenied() {
        Toast.makeText(this, "权限被拒绝后，将无法进行图片下载，友盟统计.......", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
