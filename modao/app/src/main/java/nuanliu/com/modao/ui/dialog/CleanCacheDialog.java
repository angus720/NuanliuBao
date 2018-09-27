package nuanliu.com.modao.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxDialogFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.global.App;
import nuanliu.com.modao.utils.CacheUtil;
import nuanliu.com.modao.widget.TouchTextView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Angus on 2018/06/21.
 */

public class CleanCacheDialog extends RxDialogFragment {

    @BindView(R.id.tv_sure)
    TouchTextView mTvSure;
    @BindView(R.id.tv_cancel)
    TouchTextView mTvCancel;
    private BaseActivity mActivity;
    private OnDismissListener mListener1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.transparentFrameNoMarginWindowStyle);
        setCancelable(false);
        if (getActivity() instanceof BaseActivity) {
            mActivity = (BaseActivity) getActivity();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_cleancache, container, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams layoutParams = window != null ? window.getAttributes() : null;
        window.setWindowAnimations(R.style.my_menu_animstylepopup);
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
    }


    @OnClick({R.id.tv_sure, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                if (mActivity != null) {
                    mActivity.showLoading("清理中...");
                    Observable.timer(2, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Long>() {
                                @Override
                                public void onCompleted() {
                                    if (mListener1 != null) {
                                    mListener1.onDismissBack();
                                    }
                                    mActivity.dismissLoading();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (mListener1 != null) {
                                    mListener1.onDismissBack();
                                    }
                                    mActivity.dismissLoading();
                                }

                                @Override
                                public void onNext(Long aLong) {

                                }
                            });
                }
                CacheUtil.clearAllCache(App.getContext());
                this.dismiss();
                break;
            case R.id.tv_cancel:
                this.dismiss();
                break;
        }
    }

    public interface OnDismissListener {
        void onDismissBack();
    }
    public void setDismissListener(OnDismissListener listener) {
        this.mListener1=listener;
    }

}
