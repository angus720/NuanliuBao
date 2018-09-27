package nuanliu.com.modao.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.LatAndLonBean;
import nuanliu.com.modao.global.App;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.utils.GPSUtil;
import nuanliu.com.modao.utils.OpenMapUtil;
import nuanliu.com.modao.widget.TouchTextView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by soleAnt on 2016/11/1.
 */

public class ChooseMapDialog extends RxDialogFragment {

    @BindView(R.id.tv_cancel)
    TouchTextView mTvCancel;
    private BaseActivity mActivity;

    private OnDismissListener mListener1;

    private String mprojectid;
    private String mOrderId;

    private boolean isfinishNet = false;
    private LatAndLonBean mLatAndLonBean;
    private String mLat = "";
    private String mLng = "";

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
        View inflate = inflater.inflate(R.layout.dialog_choose_map, container, false);
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
//        getLatAndLon();
    }


    @OnClick({R.id.gaode_map, R.id.baidu_map, R.id.tengxun_map, R.id.tv_cancel})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_cancel) {
            dismiss();
            return;
        }
        if (TextUtils.isEmpty(mLat) | TextUtils.isEmpty(mLng)) {
            Toast.makeText(App.getContext(), "暂无GPS信息", Toast.LENGTH_SHORT).show();
            dismiss();
        } else {
            double lng = 0;
            double lat = 0;
            String[] lngLat;
            try {
                lng = Double.parseDouble(mLng);
                lat = Double.parseDouble(mLat);
            } catch (Exception e) {
                Toast.makeText(App.getContext(), "暂无GPS信息", Toast.LENGTH_SHORT).show();
                dismiss();
                return;
            }
            switch (view.getId()) {
                case R.id.gaode_map:
                    OpenMapUtil.openGaodeMap(getActivity(), mLat, mLng);
                    break;
                case R.id.baidu_map:
                    lngLat = GPSUtil.gcj02tobd09(lng, lat);
                    OpenMapUtil.openBaiduMap(getActivity(), lngLat[1], lngLat[0]);
                    break;
                case R.id.tengxun_map:
                    OpenMapUtil.openTencentMap(getActivity(), mLat, mLng);
                    break;
            }

            dismiss();

        }

    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public void setmLng(String mLng) {
        this.mLng = mLng;
    }

    public interface OnDismissListener {
        void onDismissBack();
    }

    public void setProjectId(String projectId) {
        this.mprojectid = projectId;
    }

    public void setOrderId(String orderId) {
        this.mOrderId = orderId;
    }

    private void getLatAndLon() {
        ServiceFactory.getLatandlonapis().getLatAndLon()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LatAndLonBean>() {
                    @Override
                    public void onCompleted() {
                        isfinishNet = true;

                    }

                    @Override
                    public void onError(Throwable e) {
                        isfinishNet = true;

                    }

                    @Override
                    public void onNext(LatAndLonBean latAndLonBean) {
                        mLatAndLonBean = latAndLonBean;
                        dealWithData();

                    }
                });
    }

    private void dealWithData() {
        List<LatAndLonBean.LocationsBean> locations = mLatAndLonBean.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getPID().equals(mprojectid)) {
                mLat = locations.get(i).getLat();
                mLng = locations.get(i).getLng();
            }

        }
    }

}
