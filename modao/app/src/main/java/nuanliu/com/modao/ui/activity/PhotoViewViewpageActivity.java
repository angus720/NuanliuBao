package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.widget.photoview.PhotoView;

public class PhotoViewViewpageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.rl_topview)
    //    RelativeLayout      mRlTopview;
    //    @BindView(R.id.back)
    //    TouchRelativeLayout mBack;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private ArrayList mArrayList;

    private boolean flag = true;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_photo_list_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initIntentData();
        initView();
        initEvent();
    }

    @Override
    public void beforeSetContentView() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void initIntentData() {

        Intent intent = getIntent();
        if (intent.hasExtra(Fields.PHOTO_VIEW)) {
            mArrayList = intent.getStringArrayListExtra(Fields.PHOTO_VIEW);
        }
    }

    private void initView() {

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mArrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //ImageView photoView = new ImageView(GooglePlayApp.context);
                //    现在我们要做图片放大缩小效果
                PhotoView photoView = new PhotoView(PhotoViewViewpageActivity.this);

                photoView.setScaleY(0.98f);
                Glide.with(PhotoViewViewpageActivity.this)
                        .load(mArrayList.get(position))
                        .into(photoView);
                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                        container.removeView((View) object);
            }
        });
        mViewPager.setCurrentItem(0);
    }

    private void initEvent() {
        getToolBar().show();
    }
}
