package nuanliu.com.modao.other;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @创建者 SoleJoke
 * @创建时间 on 2017/3/20
 * @描述     此类主要是登录页面监听软键盘的显示和隐藏
 * @更新者 　$Author
 * @更新时间 　2017/3/20
 * @更新描述　　${ }
 */

public class KeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ListenerHandler";
    private View             mContentView;
    private int              mOriginHeight;
    private int              mPreHeight;
    private KeyBoardListener mKeyBoardListen;
    private boolean  lastkeyboardstae=false;

    public interface KeyBoardListener {
        /**
         * call back
         * @param isShow true is show else hidden
         * @param keyboardHeight keyboard height
         */
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public void setKeyBoardListener(KeyBoardListener keyBoardListen) {
        this.mKeyBoardListen = keyBoardListen;
    }

    public KeyboardChangeListener(Activity contextObj) {
        if (contextObj == null) {
            Log.i(TAG, "contextObj is null");
            return;
        }
        mContentView = findContentView(contextObj);
        if (mContentView != null) {
            addContentTreeObserver();
        }
    }

    private View findContentView(Activity contextObj) {
        return contextObj.findViewById(android.R.id.content);
    }

    private void addContentTreeObserver() {
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        int thistate=0;
        int currHeight = mContentView.getHeight();
        if (currHeight == 0) {
            Log.i(TAG, "currHeight is 0");
            return;
        }
        boolean hasChange = false;
        if (mPreHeight == 0) {
            mPreHeight = currHeight;
            mOriginHeight = currHeight;
        } else {
            if (mPreHeight != currHeight) {
                hasChange = true;
                mPreHeight = currHeight;
            } else {
                hasChange = false;
            }
        }
        if (hasChange) {
            boolean isShow;
            int keyboardHeight = 0;
            if (mOriginHeight == currHeight) {
                //hidden 软键盘收起来
                isShow = false;
            } else {
                //show   软键盘显示
                keyboardHeight = mOriginHeight - currHeight;
                isShow = true;
            }

            if (lastkeyboardstae  != isShow) {
                if (mKeyBoardListen != null) {
                    mKeyBoardListen.onKeyboardChange(isShow, keyboardHeight);
                }
            }

            lastkeyboardstae=isShow;
        }


    }

    public void destroy() {
        if (mContentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        }
    }
}
