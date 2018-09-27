package nuanliu.com.modao.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import nuanliu.com.modao.R;


/**
 * Created by Deity on 2016/12/12.
 */

public class NXToolBar {

    private AppCompatActivity mActivity;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TextView mTextView;
    private ActionBar mActionBar;

    public NXToolBar(AppCompatActivity activity, AppBarLayout appBarLayout) {
        mActivity = activity;
        mAppBarLayout = appBarLayout;
        mToolbar = (Toolbar) mAppBarLayout.findViewById(R.id.toolbar);
        AutoUtils.auto(mToolbar);
        mTextView = (TextView) mAppBarLayout.findViewById(R.id.title);
        mActivity.setSupportActionBar(mToolbar);
        mActionBar = mActivity.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle("");
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //关闭软键盘
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    View view = mActivity.getCurrentFocus();
                    if (imm.isActive() && view != null) {
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mActivity.finish();
            }
        });

        setElevation(0);
    }

    public NXToolBar setBackgroundColor(@ColorInt int color) {
        mToolbar.setBackgroundColor(color);
        return this;
    }

    public NXToolBar setTitleColor(@ColorInt int color) {
        mTextView.setTextColor(color);
        return this;
    }

    public NXToolBar setElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(elevation);
        }
        return this;
    }

    public NXToolBar setTitle(String title) {
        mTextView.setText(title);
        return this;
    }

    public NXToolBar setVisibility(int visibility) {
        mToolbar.setVisibility(visibility);
        return this;
    }

    public NXToolBar setTitle(int resId) {
        mTextView.setText(mActivity.getString(resId));
        return this;
    }

    public NXToolBar setNavigationOnClickListener(View.OnClickListener listener) {
        mToolbar.setNavigationOnClickListener(listener);
        return this;
    }

    //    public NXToolBar setNavigationIcon(int resId) {
//        mToolbar.setNavigationIcon(resId);
//        return this;
//    }

    public NXToolBar setNavigationIcon(@Nullable Drawable resId) {
        mToolbar.setNavigationIcon(resId);
        return this;
    }

    public NXToolBar setNavigationIcon(@DrawableRes int resId) {
        mToolbar.setNavigationIcon(resId);
        return this;
    }

    public NXToolBar setDisplayHomeAsUpEnabled(boolean show) {
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    public NXToolBar hide() {
        mActionBar.hide();
        return this;
    }

    public NXToolBar show() {
        mActionBar.show();
        return this;
    }

}
