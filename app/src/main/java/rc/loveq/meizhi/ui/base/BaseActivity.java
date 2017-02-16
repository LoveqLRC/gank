package rc.loveq.meizhi.ui.base;

import android.content.Intent;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rc.loveq.meizhi.GankApi;
import rc.loveq.meizhi.LiaoFactory;
import rc.loveq.meizhi.R;
import rc.loveq.meizhi.ui.AboutActivity;
import rc.loveq.meizhi.ui.WebActivity;
import rc.loveq.meizhi.util.Once;
import rc.loveq.meizhi.util.Toasts;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class BaseActivity extends SwipeBackActivity {

    public static final GankApi sGankIO= LiaoFactory.getGankIOSingleton();
    //所有的 Subscription对象可以添加到CompositeSubscription，
    // 然后可以使用CompositeSubscription.unsubscribe()方法在同一时间进行退订(unsubscribed)。
    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription(){
        if (mCompositeSubscription==null) {
            mCompositeSubscription=new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    public void addSubscription(Subscription subscription){

        if (mCompositeSubscription==null) {
            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_login:
                loginGithub();
                return  true;
            case rc.loveq.meizhi.R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 登录Github
     */
    private void loginGithub() {
        new Once(this).show(R.string.action_github_login, new Once.OnceCallback() {
            @Override
            public void onOnce() {
                Toasts.showLongX2(getString(R.string.tip_login_github));
            }
        });
        String url=getString(R.string.url_login_github);
        Intent intent = WebActivity.newIntent(this, url, getString(R.string.action_github_login));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
