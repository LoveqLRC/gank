package rc.loveq.meizhi.event;

import android.view.View;

import rc.loveq.meizhi.data.entity.MeiZhi;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 20:38
 * Email:664215432@qq.com
 */

public interface OnMeizhiTouchListener {
    void onTouch(View v, View meizhiView, View card, MeiZhi meizhi);
}
