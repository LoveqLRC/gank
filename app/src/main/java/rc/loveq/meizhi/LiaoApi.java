package rc.loveq.meizhi;

import rc.loveq.meizhi.data.LGankData;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 10:38
 * Email:664215432@qq.com
 */

public interface LiaoApi {
    @Headers({ "X-LC-Id: 0azfScvBLCC9tAGRAwIhcC40",
            "X-LC-Key: gAuE93qAusvP8gk1VW8DtOUb",
            "Content-Type: application/json"})
    @GET("Gank?limit=1")
    Observable<LGankData> getLGankData(
            @Query("where") String where);
}
