package rc.loveq.meizhi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 23:30
 * Email:664215432@qq.com
 */

public class LiaoRetrofit {
    final static Gson gson=new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    public GankApi gankService;
    public LiaoApi liaoService;

    LiaoRetrofit(){
        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        if (LiaoFactory.isDebug) {
            HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpclient.addInterceptor(logging);
        }
        httpclient.connectTimeout(12, TimeUnit.SECONDS);
        OkHttpClient client = httpclient.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://gank.io/api/")
                .client(client)
                //网络请求默认在io()线程
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit gankRest = builder.build();
        builder.baseUrl("https://leancloud.cn:443/1.1/classes/");
        Retrofit liaoRest = builder.build();
        gankService = gankRest.create(GankApi.class);
        liaoService = liaoRest.create(LiaoApi.class);
    }

    public GankApi getGankService() {
        return gankService;
    }

    public LiaoApi getLiaoService() {
        return liaoService;
    }
}
