package rc.loveq.meizhi.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 20:45
 * Email:664215432@qq.com
 */

public class Once {
    private final Context mContext;
    public SharedPreferences mSharedPreferences;

    public Once(Context context){
        this.mContext=context;
        mSharedPreferences = context.getSharedPreferences("once", Context.MODE_PRIVATE);
    }
    public void show(String targetKey,OnceCallback callback){
        boolean isSecondTime = mSharedPreferences.getBoolean(targetKey, false);
        if (!isSecondTime) {
            callback.onOnce();
            mSharedPreferences.edit().putBoolean(targetKey,true).apply();
        }
    }
    public void show(int resId,OnceCallback callback){
        show(mContext.getString(resId),callback);
    }
    public interface OnceCallback{
        void onOnce();
    }
}
