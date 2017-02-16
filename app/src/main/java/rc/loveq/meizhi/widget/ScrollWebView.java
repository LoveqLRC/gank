package rc.loveq.meizhi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/2/14 16:19
 * Email:664215432@qq.com
 */

public class ScrollWebView extends WebView {

    public static interface OnScrollListener {
        public void onScroll(final int l, final int t, final int oldl,
                             final int oldt);
    }

    private OnScrollListener mOnScrollListener;

    public ScrollWebView(final Context context) {
        super(context);
    }

    public ScrollWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(final Context context, final AttributeSet attrs,
                         final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(l, t, oldl, oldt);
        }
    }

    public OnScrollListener getOnScrollListener() {
        return mOnScrollListener;
    }

    public void setOnScrollListener(final OnScrollListener onScrollChangedCallback) {
        mOnScrollListener = onScrollChangedCallback;
    }
}
