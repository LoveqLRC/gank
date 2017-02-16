package rc.loveq.meizhi;

import android.app.Application;
import android.content.Context;

import com.litesuits.orm.LiteOrm;

import rc.loveq.meizhi.util.Toasts;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 17:34
 * Email:664215432@qq.com
 */

public class App extends Application {

    public static Context sContext;
    public static  final String dbName="gank.db";
    public static LiteOrm sDb;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Toasts.register(this);
        sDb=LiteOrm.newSingleInstance(this, dbName);
        if (BuildConfig.DEBUG){
            sDb.setDebugged(true);
        }
    }
}
