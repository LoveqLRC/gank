package rc.loveq.meizhi.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rc.loveq.meizhi.LiaoFactory;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.data.LGankData;
import rc.loveq.meizhi.data.entity.Gank;
import rc.loveq.meizhi.data.entity.LGank;
import rc.loveq.meizhi.ui.adapter.GankListAdapter;
import rc.loveq.meizhi.util.Once;
import rc.loveq.meizhi.util.Toasts;
import rc.loveq.meizhi.widget.LoveVideoView;
import rc.loveq.meizhi.widget.VideoImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/2/2 18:34
 * Email:664215432@qq.com
 */

public class GankFragment extends Fragment {
    public static final String ARG_YEAR="year";
    public static final String ARG_MONTH="month";
    public static final String ARG_DAY="day";
    public int mYear;
    public int mMonth;
    public int mDay;
    public GankListAdapter mGankListAdapter;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.stub_empty_view)
    ViewStub mEmptyViewStub;
    @BindView(R.id.stub_video_view)
    ViewStub mVideoViewStub;
    @BindView(R.id.video_image)
    VideoImageView mVideoImageView;
    public List<Gank> mGankList;
    String mVideoPreviewUrl;
    private boolean mIsVideoViewInflated=false;
    public LoveVideoView mLoveVideoView;

    public static GankFragment newIntance(int year,int month ,int day){
        GankFragment gankFragment=new GankFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(ARG_YEAR,year);
        bundle.putInt(ARG_MONTH,month);
        bundle.putInt(ARG_DAY,day);
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankList = new ArrayList<>();
        mGankListAdapter = new GankListAdapter(mGankList);
        parseArguments();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    private void parseArguments() {
        Bundle bundle = getArguments();
        mYear = bundle.getInt(ARG_YEAR);
        mMonth = bundle.getInt(ARG_MONTH);
        mDay = bundle.getInt(ARG_DAY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this,view);
        initRecyclerView();
        setVideoViewPosition(getResources().getConfiguration());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGankList.size()==0) {
            loadData();
        }
        if (mVideoPreviewUrl!=null) {
            Glide.with(this).load(mVideoPreviewUrl).into(mVideoImageView);
        }
    }

    private void loadData() {
        loadVideoPreview();
    }

    private void loadVideoPreview() {
        String where=String.format("{\"tag\":\"%d-%d-%d\"}",mYear,mMonth,mDay);
        LiaoFactory.getLiaoSingleton()
                    .getLGankData(where)
                    .map(new Func1<LGankData, List<LGank>>() {
                        @Override
                        public List<LGank> call(LGankData data) {
                            return data.results;
                        }
                    }).single(new Func1<List<LGank>, Boolean>() {
            @Override
            public Boolean call(List<LGank> ganks) {
                return ganks.size()>0;
            }
        }).map(new Func1<List<LGank>, LGank>() {
            @Override
            public LGank call(List<LGank> ganks) {
                return ganks.get(0);
            }
        }).observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Action1<LGank>() {
             @Override
             public void call(LGank gank) {
                 startPreview(gank.preview);
             }
         }, new Action1<Throwable>() {
             @Override
             public void call(Throwable throwable) {
                 getOldVideoPreview(new OkHttpClient());
             }
         })      ;
    }

    private void getOldVideoPreview(OkHttpClient client) {
        String url = "http://gank.io/" + String.format("%s/%s/%s", mYear, mMonth, mDay);
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Toasts.showShort("onResponse"+s);

            }
        });
    }

    private void startPreview(String preview) {
        mVideoPreviewUrl=preview;
        if (preview!=null&&mVideoImageView!=null){
//            TODO post
            Glide.with(this).load(preview).into(mVideoImageView);
        }
    }


    private void initRecyclerView() {
        LinearLayoutManager manger=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manger);
        mRecyclerView.setAdapter(mGankListAdapter);
    }
    private void setVideoViewPosition(Configuration configuration) {
        switch (configuration.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE://横屏
                if(mIsVideoViewInflated){
                    mVideoViewStub.setVisibility(View.VISIBLE);
                }else{
                    mLoveVideoView = (LoveVideoView) mVideoViewStub.inflate();
                    mIsVideoViewInflated=true;
                    final String tip=getString(R.string.tip_video_play);
                    new Once(mLoveVideoView.getContext()).show(tip, new Once.OnceCallback() {
                        @Override
                        public void onOnce() {
                            Snackbar.make(mLoveVideoView,tip,Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.i_konw, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    }).show();
                        }
                    });
                }
                if (mGankList.size()>0&&mGankList.get(0).type.equals("休息视频")) {
                    mLoveVideoView.loadUrl(mGankList.get(0).url);
                }
                break;
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_UNDEFINED:
            default:
                mVideoViewStub.setVisibility(View.GONE);
                    break;

        }

    }

}
