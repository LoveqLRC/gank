package rc.loveq.meizhi.data.entity;

import com.litesuits.orm.db.annotation.Table;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/29 22:45
 * Email:664215432@qq.com
 */
@Table("Test")
public class Test extends Soul {
    public String name;
    public int age;

    public Test(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
