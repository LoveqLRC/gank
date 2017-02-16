package rc.loveq.meizhi.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import rc.loveq.meizhi.R;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 15:10
 * Email:664215432@qq.com
 */

public abstract class ToolBarActivity extends BaseActivity {
    public AppBarLayout mAppBar;
    public Toolbar mToolBar;
    //AppBar是否隐藏，false是不隐藏
    protected boolean mIsHidden=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mAppBar = ((AppBarLayout) findViewById(R.id.app_bar_layout));
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        //ToolBar的子类必须有AppBarLayout和Toolbar控件
        if (mAppBar==null||mToolBar==null) {
            throw new IllegalStateException("The subclass of ToolbarActivity must contain a toolbar.");
        }
        mToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToolBarClick();
            }
        });
        setSupportActionBar(mToolBar);
        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        if (Build.VERSION.SDK_INT>=21){
            mAppBar.setElevation(10.6f);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return  true;
        }else{
        return super.onOptionsItemSelected(item);
        }
    }
    protected void setAppBarAlpha(float alpha){
        mAppBar.setAlpha(alpha);
    }
    protected void hideOrShowToolBar(){
        mAppBar.animate()
                .translationY(mIsHidden?0:-mAppBar.getMeasuredHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden=!mIsHidden;
    }


    /**
     * 是否可以返回上一个Activity
     * @return
     */
    public boolean canBack(){return  false;}
    /**
     * 当ToolBar被点击
     */
    public  void onToolBarClick(){};


    /*
    返回布局代码
     */
    protected abstract int getLayoutId();
}
