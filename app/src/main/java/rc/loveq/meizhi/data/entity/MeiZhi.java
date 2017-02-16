package rc.loveq.meizhi.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 22:40
 * Email:664215432@qq.com
 */
@Table("meizhis")
public class MeiZhi extends Soul {
    @Column("url")public String url;
    @Column("type")public String type;
    @Column("desc")public String desc;
    @Column("who")public String who;
    @Column("used")public boolean used;
    @Column("createdAt")public Date createdAt;
    @Column("updatedAt")public Date updatedAt;
    @Column("publishedAt")public Date publishedAt;
    @Column("imageWidth")public int imageWidth;
    @Column("imageHeight")public int imageHeight;

    @Override
    public String toString() {
        return "MeiZhi{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", who='" + who + '\'' +
                ", used=" + used +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", publishedAt=" + publishedAt +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                '}';
    }

    public MeiZhi(String url, String type, String desc, String who, boolean used, Date createdAt, Date updatedAt, Date publishedAt, int imageWidth, int imageHeight) {
        this.url = url;
        this.type = type;
        this.desc = desc;
        this.who = who;
        this.used = used;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.publishedAt = publishedAt;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

}
