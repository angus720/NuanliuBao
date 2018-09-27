package nuanliu.com.modao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/1/19
 * @描述
 * @更新者 　$Author
 * @更新时间 　2017/1/19
 * @更新描述　　${ToDo}
 */

public class ElasticScrollView extends ScrollView {
    private View  inner;
    private float y;
    private Rect    normal          = new Rect();
    private boolean animationFinish = true;

    public ElasticScrollView(Context context) {
        super(context);
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
//        super.onFinishInflate();

        int childCount = getChildCount();
        Log.i("ElasticScrollView", "childCount:" + childCount);
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        if (animationFinish) {
            int action = ev.getAction();
            Log.i("ElasticScrollView", "ev.getSize():" + ev.getSize());
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    //System.out.println("ACTION_DOWN");
                    y = ev.getY();
                    super.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_UP:
                    //				System.out.println("ACTION_UP");
                    y = 0;
                    if (isNeedAnimation()) {
                        animation();
                    }
                    super.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    //				System.out.println("ACTION_MOVE");
                    float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();
                    int deltaY = (int) (preY - nowY);
                    // 滚动
//                    scrollBy(0, deltaY);
                    y = nowY;
                    // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                    if (isNeedMove()) {
                        if (normal.isEmpty()) {
                            // 保存正常的布局位置
                            normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                        }
                        // 移动布局
                        inner.layout(inner.getLeft(), inner.getTop() - deltaY / 3, inner.getRight(), inner.getBottom() - deltaY / 3);
                    } else {
                        super.onTouchEvent(ev);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // 开启动画移动

    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - inner.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                inner.clearAnimation();
                // 设置回到正常的布局位置
                inner.layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }
        });
        inner.startAnimation(ta);
    }

    //  是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // 是否需要移动布局,内部inner 不滑动的时候 ，这时候就需要粘性滑动了
    public boolean isNeedMove() {

        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();

        //scrollY=0  下拉 scrollY=
        if (scrollY == 0 || scrollY == offset) {

            Log.i("ElasticScrollView", "true:" + true);
            return true;
        }
        Log.i("ElasticScrollView", "false:" + false);
        return false;

    }


}
