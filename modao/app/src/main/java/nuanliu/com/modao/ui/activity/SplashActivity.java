package nuanliu.com.modao.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.ui.MainActivity;
import nuanliu.com.modao.utils.SpUtil;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_skip)
    TextView mTvSkip;

    private long mSkiptime;

    private ValueAnimator mValueAnimator;

    private boolean isSkip = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWindow();
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_splash;

    }

    private void initWindow() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        logicalPro();
    }

    private void logicalPro() {

        mSkiptime = 2;

        Observable
                .timer(1, TimeUnit.SECONDS)
                .repeat(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                        mValueAnimator.removeAllUpdateListeners();
                        if (!isSkip) {
//                            if (SpUtil.isUseGuide()) {
//                                startActivity(new Intent(SplashActivity.this, GuideActivity.class));
//                                finish();
//                            } else
                            if (SpUtil.isLogin()) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                        mTvSkip.setText("跳过" + mSkiptime + "秒");
                        mSkiptime--;

                    }
                });
    }

    private void initView() {

    }

    private void initData() {

        mValueAnimator = ValueAnimator.ofFloat(0, 1f);
        mValueAnimator.setRepeatCount(100000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float i = 0.0f;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
            }
        });
        mValueAnimator.start();
    }


    @OnClick(R.id.tv_skip)
    public void onClick() {

        mValueAnimator.removeAllUpdateListeners();
        isSkip = true;
//        if (SpUtil.isUseGuide()) {
//            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
//            finish();
//        } else
        if (SpUtil.isLogin()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }
}
