package rc.loveq.meizhi.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Author：Rc
 * Csdn：http://blog.csdn.net/loveqrc
 * 0n 2017/2/3 09:28
 * Email:664215432@qq.com
 */

public class VideoImageView extends ImageView implements Animator.AnimatorListener {
    private boolean scale=false;

    public VideoImageView(Context context) {
        this(context,null);
    }

    public VideoImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nextAnimation();
    }

    private void nextAnimation() {
        AnimatorSet animatorSet=new AnimatorSet();
        if (scale){
            animatorSet.playTogether(ObjectAnimator.ofFloat(this,"scaleX",1.5f,1f)
                    ,ObjectAnimator.ofFloat(this,"scaleY",1.5f,1f));
        }else{
            animatorSet.playTogether(ObjectAnimator.ofFloat(this,"scaleX",1f,1.5f),
                    ObjectAnimator.ofFloat(this,"scaleY",1f,1.5f));
        }
        animatorSet.setDuration(10000);
        animatorSet.addListener(this);
        animatorSet.start();
        scale=!scale;
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        nextAnimation();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
