package nuanliu.com.modao.ui.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nuanliu.com.modao.BuildConfig;
import nuanliu.com.modao.R;
import nuanliu.com.modao.ui.MainActivity;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.convenientbanner.ConvenientBanner;
import nuanliu.com.modao.widget.convenientbanner.holder.CBViewHolderCreator;
import nuanliu.com.modao.widget.convenientbanner.holder.Holder;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.banner_wellcome)
    ConvenientBanner bannerWellcome;
    @BindView(R.id.guide_start)
    TextView        guideStart;
    private int[] imgs=new int[]{R.mipmap.splash_img, R.mipmap.splash_img, R.mipmap.splash_img};
    private List<Drawable> bannerdata=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
//        bannerWellcome= (ConvenientBanner) findViewById(R.id.banner_wellcome);
//        guideStart = (TextView) findViewById(R.id.guide_start);

        ButterKnife.bind(this);
        iniData();
        logicalPro();
    }
    private void iniData() {

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        //向导页面banner图

        for (int i = 0; i < imgs.length; i++) {
            bannerdata.add(ContextCompat.getDrawable(this,imgs[i]));
//
        }
        bannerWellcome.setCanLoop(false);
        bannerWellcome.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new GuideHolder();
            }
        }, bannerdata).setPageIndicator(new int[]{R.drawable.ic_page_indicator,
                R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        //事件初始化
        guideStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存版本号
                SpUtil.saveIsUseGuide(BuildConfig.VERSION_CODE);
                if (SpUtil.isLogin()) {
                    IntentUtils.startActivity(GuideActivity.this, MainActivity.class);
                    finish();
                } else {
                    IntentUtils.startActivity(GuideActivity.this, LoginActivity.class);
                    finish();
                }


            }
        });
    }

    //    逻辑处理
    private void logicalPro() {

        bannerWellcome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == (imgs.length - 1)) {
                    guideStart.setVisibility(View.VISIBLE);

                } else {
                    guideStart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    public static class GuideHolder implements Holder<Drawable> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Drawable data) {
            imageView.setImageDrawable(data);
        }
    }
}
