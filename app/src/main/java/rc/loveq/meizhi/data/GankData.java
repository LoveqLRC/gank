package rc.loveq.meizhi.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import rc.loveq.meizhi.data.entity.Gank;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 07:59
 * Email:664215432@qq.com
 */

public class GankData {
    public List<String> category;
    public Results results;
    public class Results{
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("休息视频")
        public List<Gank> restVideoList;
        @SerializedName("前端")
        public List<Gank> qianduanList;
        @SerializedName("拓展资源")
        public List<Gank> expandedList;
        @SerializedName("瞎推荐")
        public List<Gank> randomList;
        @SerializedName("福利")
        public List<Gank> fuliList;
        @SerializedName("App")
        public List<Gank> appList;



    }
}
