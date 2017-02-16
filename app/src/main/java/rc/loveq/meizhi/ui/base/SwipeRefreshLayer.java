package rc.loveq.meizhi.ui.base;

import rc.loveq.meizhi.widget.MultiSwipeRefreshLayout;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/1/30 18:35
 * Email:664215432@qq.com
 */

public interface SwipeRefreshLayer {
    /*请求数据刷新*/
    void requestDataRefresh();
    /*设置刷新按钮是否显示 refresh：true为显示*/
    void setRefresh(boolean refresh);

    /*
    scale:刷新那个圆形进度是是否缩放,如果为true表示缩放,圆形进度图像就会从小到大展示出来,为false就不缩放
    start、end:start和end就是那刷新进度条展示的相对于默认的展示位置,start和end组成一个范围，
            在这个y轴范围就是那个圆形进度ProgressView展示的位置
     */
    void setProgressViewOffset(boolean scale, int start, int end);
    /*向上滚动回调*/
    void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback);

}
