package rc.loveq.meizhi;

import rc.loveq.meizhi.data.GankData;
import rc.loveq.meizhi.data.MeiZhiData;
import rc.loveq.meizhi.data.RestVideoData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 21:09
 * Email:664215432@qq.com
 */

public interface GankApi {
    @GET("data/福利/" + LiaoFactory.meizhiSize + "/{page}")
    Observable<MeiZhiData> getMeiZhiData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/休息视频/" + LiaoFactory.meizhiSize + "/{page}")
    Observable<RestVideoData> getRestVideoData(@Path("page") int page);


}
