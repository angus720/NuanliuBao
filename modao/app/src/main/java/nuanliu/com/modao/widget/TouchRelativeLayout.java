package nuanliu.com.modao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.drawable.TouchEffectDrawable;
import net.qiujuer.genius.ui.drawable.effect.EffectFactory;
import net.qiujuer.genius.ui.drawable.factory.ClipFilletFactory;


public class TouchRelativeLayout extends RelativeLayout implements TouchEffectDrawable.PerformClicker {

    private TouchEffectDrawable mTouchDrawable;
    private int                 mTouchColor;

    public TouchRelativeLayout(Context context) {
        super(context);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, net.qiujuer.genius.ui.R.attr.gImageViewStyle, net.qiujuer.genius.ui.R.style.Genius_Widget_ImageView);
    }

    public TouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, net.qiujuer.genius.ui.R.style.Genius_Widget_ImageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        setClickable(true);
        if (attrs == null)
            return;
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, net.qiujuer.genius.ui.R.styleable.ImageView, defStyleAttr, defStyleRes);
        int touchEffect = a.getInt(net.qiujuer.genius.ui.R.styleable.ImageView_gTouchEffect, EffectFactory.TOUCH_EFFECT_RIPPLE);
        int touchColor = a.getColor(net.qiujuer.genius.ui.R.styleable.ImageView_gTouchColor, Ui.TOUCH_PRESS_COLOR);
        float touchDurationRate = a.getFloat(net.qiujuer.genius.ui.R.styleable.ImageView_gTouchDurationRate, 0.2f);
        a.recycle();
        setTouchEffect(touchEffect);
        setTouchColor(touchColor);
        setTouchDuration(touchDurationRate);

        ClipFilletFactory touchFactory = new ClipFilletFactory(0);
        if (!this.isInEditMode()) {
            setTouchClipFactory(touchFactory);
        }
    }

    public void setTouchColor(int touchColor) {
        if (mTouchDrawable != null && touchColor != -1 && touchColor != mTouchColor) {
            mTouchColor = touchColor;
            mTouchDrawable.setColor(touchColor);
            invalidate();
        }
    }

    public void setTouchClipFactory(TouchEffectDrawable.ClipFactory factory) {
        if (mTouchDrawable != null) {
            mTouchDrawable.setClipFactory(factory);
        }
    }

    /**
     * Set the touch animation duration.
     * This setting about enter animation
     * and exit animation.
     * <p/>
     * Default:
     * EnterDuration: 280ms
     * ExitDuration: 160ms
     * FactorRate: 1.0
     * <p/>
     * This set will calculation: factor * duration
     * This factor need > 0
     *
     * @param factor Touch duration rate
     */
    public void setTouchDuration(float factor) {
        if (mTouchDrawable != null) {
            mTouchDrawable.setEnterDuration(factor);
            mTouchDrawable.setExitDuration(factor);
        }
    }

    public void setTouchEffect(int touchEffect) {
        if (touchEffect == 0)
            mTouchDrawable = null;
        else {
            if (mTouchDrawable == null) {
                mTouchDrawable = new TouchEffectDrawable();
                mTouchDrawable.getPaint().setColor(mTouchColor);
                mTouchDrawable.setCallback(this);
            }

            mTouchDrawable.setEffect(EffectFactory.creator(touchEffect));

            // We want set this LayerType type on Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        TouchEffectDrawable drawable = mTouchDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        Drawable drawable = mTouchDrawable;
        return (drawable != null && who == drawable) || super.verifyDrawable(who);
    }

    @Override
    public boolean performClick() {
        final TouchEffectDrawable d = mTouchDrawable;

        if (d != null) {
            return d.performClick(this) && super.performClick();
        } else
            return super.performClick();
    }

    @Override
    public void postPerformClick() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                performClick();
            }
        };

        if (!this.post(runnable)) {
            performClick();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        final boolean ret = super.onTouchEvent(event);

        // send to touch drawable
        final TouchEffectDrawable d = mTouchDrawable;
        if (ret && d != null && isEnabled()) {
            d.onTouch(event);
        }

        return ret;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the effect on the image
        final TouchEffectDrawable d = mTouchDrawable;
        if (d != null) {
            d.draw(canvas);
        }
    }
}
