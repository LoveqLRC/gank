package rc.loveq.meizhi;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 18:20
 * Email:664215432@qq.com
 */

public class LiaoFactory {
    public static final Object monitor=new Object();
    public static final int meizhiSize=10;
    //是否开启Okhttp log
    public static boolean isDebug=true;

     static GankApi sGankIOSingleton=null;
     static LiaoApi sLiaoSingleton=null;
    //TODO:补充剩下属性


    public static GankApi getGankIOSingleton(){
        synchronized (monitor){
            if (sGankIOSingleton==null) {
            sGankIOSingleton = new LiaoRetrofit().getGankService();
            }
            return sGankIOSingleton;
        }
    }
    public static LiaoApi getLiaoSingleton(){
        synchronized (monitor){
            if (sLiaoSingleton==null) {
            sLiaoSingleton = new LiaoRetrofit().getLiaoService();
            }
            return sLiaoSingleton;
        }
    }

}
