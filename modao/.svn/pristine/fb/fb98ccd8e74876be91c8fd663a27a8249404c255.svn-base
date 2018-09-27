package nuanliu.com.modao.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhongtao on 2018/6/28
 */
public class MySpannableString {
    private static TextView mTxt;
    private static int start;
    private static int end;
    private static String ColorString;
    private static Boolean flag;

    public MySpannableString(TextView mTxt, int start, int end, String ColorString, Boolean flag) {
        this.mTxt = mTxt;
        this.start = start;
        this.end = end;
        this.ColorString = ColorString;
        this.flag = flag;
        setSpan();
    }

    private static MySpannableStringClick mOnGestureLockViewListener;

    public static void setSpan() {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mOnGestureLockViewListener.SpanClick();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.parseColor(ColorString));
                ds.setUnderlineText(flag);
                ds.clearShadowLayer();
            }
        };
        SpannableString spannableString = new SpannableString(mTxt.getText().toString());
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxt.setText(spannableString);
        mTxt.setMovementMethod(LinkMovementMethod.getInstance());//点击
        avoidHintColor(mTxt);
    }
    /*
     *设置textView 点击背景色
     * */

    private static void avoidHintColor(View view) {
        if (view instanceof TextView)
            ((TextView) view).setHighlightColor(Color.parseColor("#00000000"));
    }

    /**
     * 设置回调接口
     *
     * @param listener
     */
    public void setMySpannableStringClick(MySpannableStringClick listener) {
        this.mOnGestureLockViewListener = listener;
    }

    public interface MySpannableStringClick {
        void SpanClick();
    }

}
