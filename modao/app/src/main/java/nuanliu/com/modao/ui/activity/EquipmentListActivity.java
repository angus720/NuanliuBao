package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.DeviceItemBean;
import nuanliu.com.modao.bean.DeviceListBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.EquipmentListAdapter;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 设备列表
 */

public class EquipmentListActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    @BindView(R.id.multiplestatusview_all)
    MultipleStatusView mMultiplestatusview;
    @BindView(R.id.xrefreshview)
    XRefreshView mXrefreshview;

    private List<DeviceItemBean> mDatas = new ArrayList<>();
    private EquipmentListAdapter<DeviceItemBean> mAdapter;

    private int mStartPage = 1;
    private int mPagesize = 10;

    private boolean menableLoadmore = false;

    private boolean isFirst = true;

    private int flag = 2;
    private static final int REQUEST_EQUIPMENT_LIST = 105;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_equipment_list;
    }

    private void initView() {
        getToolBar().setTitle("设备列表");
        mXrefreshview.setPinnedTime(1000);
        mXrefreshview.setMoveForHorizontal(true);
//        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);

        mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        mXrefreshview.setXRefreshViewListener(this);

        mAdapter = new EquipmentListAdapter<>(this);
//        mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        mRvRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRvRecyclerview.setAdapter(mAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        //设置动画时间
        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        mRvRecyclerview.setItemAnimator(animator);
    }

    private void initData() {
        mMultiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceList(true);
            }
        });

        getDeviceList(true);
    }

    @OnClick({R.id.tll_add_new})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tll_add_new:
                Intent intent = new Intent(this, OnlinePaymentActivity.class);
                intent.putExtra(Fields.FLAG, flag);
                startActivity(intent);
                break;
        }
    }

    private void getDeviceList(final boolean isrefresh) {
        showLoading("加载中");
        mMultiplestatusview.showContent();
        ServiceFactory
                .getApis()
                .getUserDeviceList("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<DeviceListBean>bindToLifecycle())
                .subscribe(new Subscriber<DeviceListBean>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMultiplestatusview.showError();
                        dismissLoading();
                    }

                    @Override
                    public void onNext(DeviceListBean deviceListBean) {
                        if (deviceListBean.getStatus() == 1) {
                            refreshViewByData(deviceListBean.getBody().getDatas(), isrefresh);
                        } else {
                            mMultiplestatusview.showEmpty();
                        }
                    }
                });
    }

    private void refreshViewByData(List<DeviceListBean.BodyBean.DatasBean> list, final boolean isrefresh) {
        mDatas.clear();
        if (list == null || list.size() == 0) {

            mXrefreshview.setLoadComplete(true);

            if (isrefresh) {
                mMultiplestatusview.showEmpty();
            }

            return;
        }
        for (int i = 0; i < list.size(); i++) {
            DeviceListBean.BodyBean.DatasBean datasBean = list.get(i);
            mDatas.add(new DeviceItemBean(datasBean.getEui64(), datasBean.getTemp(), datasBean.getAddress(), datasBean.getHumidity(), datasBean.getCommunity()));
        }
        if (isrefresh) {
            mAdapter.clear();
            mAdapter.addData(mDatas);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addData(mDatas);
            mAdapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (menableLoadmore) {

                        mXrefreshview.stopLoadMore(true);

                    } else {
                        mXrefreshview.setLoadComplete(true);
                    }
                }
            }, 500);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            onRefresh(true);
        }
        isFirst = false;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        mXrefreshview.setLoadComplete(false);
        menableLoadmore = true;
        mStartPage = 1;
        getDeviceList(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mXrefreshview.stopRefresh();
            }
        }, 510);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        if (menableLoadmore) {
            mStartPage++;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    getDeviceList(false);
                }
            }, 500);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mXrefreshview.setLoadComplete(true);
                }
            }, 500);
        }
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.hasExtra(Fields.BIND_ADDRESS)) {
                address = data.getStringExtra(Fields.BIND_ADDRESS);
//                mDatas.add(new EquipmentBean());
            }
        }
    }
}
