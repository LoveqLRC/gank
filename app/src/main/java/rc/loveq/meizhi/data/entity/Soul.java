package rc.loveq.meizhi.data.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 22:37
 * Email:664215432@qq.com
 */

public class Soul implements Serializable{
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_ID")
    public int id;
}
