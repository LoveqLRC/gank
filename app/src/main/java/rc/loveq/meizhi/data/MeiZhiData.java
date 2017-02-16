package rc.loveq.meizhi.data;

import java.util.List;

import rc.loveq.meizhi.data.entity.MeiZhi;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 09:07
 * Email:664215432@qq.com
 */

public class MeiZhiData extends BaseData{
    public List<MeiZhi> results;

    @Override
    public String toString() {
        return "MeiZhiData{" +
                "results=" + results +
                '}';
    }
}
