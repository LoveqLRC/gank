package rc.loveq.meizhi.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/2/2 21:57
 * Email:664215432@qq.com
 */
public class StringStyles {
    public static CharSequence format(Context context, String src, int stringStyle) {
        SpannableString spannableString=new SpannableString(src);
        spannableString.setSpan(new TextAppearanceSpan(context,stringStyle),0,src.length(),0);
        return spannableString;
    }
}
