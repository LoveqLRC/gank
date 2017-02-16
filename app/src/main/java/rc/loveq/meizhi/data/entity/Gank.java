package rc.loveq.meizhi.data.entity;

import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

import rc.loveq.meizhi.data.BaseData;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 07:47
 * Email:664215432@qq.com
 */
@Table("ganks")
public class Gank extends BaseData {
    public Date createdAt;
    public String desc;
    public Date publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
    public Date updatedAt;
}
