package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.ResidentItemBean;
import nuanliu.com.modao.bean.ResidentListBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.PaymentUserAdapter;
import nuanliu.com.modao.ui.footer.ResidentListFooter;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.TouchLinearLayout;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 缴费用户列表
 */

public class OnlinePaymentActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    @BindView(R.id.multiplestatusview_all)
    MultipleStatusView mMultiplestatusviewAll;
    @BindView(R.id.xrefreshview)
    XRefreshView mXrefreshview;
    @BindView(R.id.tll_pay_record)
    TouchLinearLayout tllPayRecord;

    private int flag;

    private List<ResidentItemBean> mDatas = new ArrayList<>();
    private PaymentUserAdapter<ResidentItemBean> mAdapter;
    private final LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    private int mStartPage = 1;
    private int mPagesize = 20;

    private boolean menableLoadmore = false;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_payment;
    }

    private void initEvent() {

        mAdapter.setOnItemClickListener(new PaymentUserAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ResidentItemBean bean, int position) {
                if (flag == 1) {
                    Intent intent = new Intent();
                    intent.putExtra(Fields.RESIDENT_ID, bean.getResidentid());
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (flag == 2) {
                    Intent intent1 = new Intent(OnlinePaymentActivity.this, DeviceBindingActivity.class);
                    intent1.putExtra(Fields.RESIDENT_ID, bean.getResidentid());
                    startActivity(intent1);
                } else {
                    Intent intent2 = new Intent(OnlinePaymentActivity.this, PaymentDetailActivity.class);
                    intent2.putExtra(Fields.RESIDENT_ID, bean.getResidentid());
                    intent2.putExtra(Fields.COMPANY_NAME, bean.getCompanyName());
                    startActivity(intent2);
                }
            }
        });
    }

    private void initView() {
        getToolBar().setTitle("在线缴费");
        if (null != getIntent()) {
            flag = getIntent().getIntExtra("flag", 0);
        }
        mXrefreshview.setPinnedTime(1000);
        mXrefreshview.setMoveForHorizontal(true);
        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);

        mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        mXrefreshview.setXRefreshViewListener(this);

        mAdapter = new PaymentUserAdapter<>(this);
        mAdapter.setCustomLoadMoreView(new ResidentListFooter(this));
        mRvRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRvRecyclerview.setAdapter(mAdapter);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        //设置动画时间
        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        mRvRecyclerview.setItemAnimator(animator);
    }

    private void initData() {
        mMultiplestatusviewAll.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResidentList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, true);
            }
        });

        getResidentList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, true);
    }

    private void getResidentList(String userName, int startPage, int pageSize, final boolean isrefresh) {
        showLoading("加载中");
        mMultiplestatusviewAll.showContent();
        ServiceFactory
                .getApis()
                .bdResidentList(userName, startPage, pageSize)
                .delaySubscription(510, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<ResidentListBean>bindToLifecycle())
                .subscribe(new Subscriber<ResidentListBean>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMultiplestatusviewAll.showError();
                        dismissLoading();
                    }

                    @Override
                    public void onNext(ResidentListBean residentListBean) {
                        if (residentListBean.getStatus() == 1) {
                            refreshViewByData(residentListBean.getBody().getList(), isrefresh);
                        } else {
//                            mMultiplestatusviewAll.showEmpty();
                            emptyView();
                        }
                    }
                });
    }

    private void emptyView() {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.default_empty_view, null);
        Button btnAddAddress = (Button) emptyView.findViewById(R.id.add_payment_address);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(OnlinePaymentActivity.this, AddressBindActivity.class);
            }
        });

        mMultiplestatusviewAll.showEmpty(emptyView, mLayoutParams);
    }

    private void refreshViewByData(List<ResidentListBean.BodyBean.ListBean> list, boolean isrefresh) {
        mDatas.clear();
        if (list == null || list.size() == 0) {

            mXrefreshview.setLoadComplete(true);

            if (isrefresh) {
//                mMultiplestatusviewAll.showEmpty();
                emptyView();
            }

            return;
        }
        //判断是否可以加载更多处理
        if (list.size() < mPagesize) {
            menableLoadmore = false;
        } else {
            menableLoadmore = true;
        }
        for (int i = 0; i < list.size(); i++) {
            ResidentListBean.BodyBean.ListBean datasBean = list.get(i);
            mDatas.add(new ResidentItemBean(datasBean.getResidentid(), datasBean.getTelephone(), datasBean.getAddress(), datasBean.getCity(), datasBean.getHouseholder(),
                    datasBean.getCompanyName(), datasBean.getCompanyid(), datasBean.getAccount_h(), datasBean.getBuildingid(), datasBean.getBuildingNumber(), datasBean.getUnitid(),
                    datasBean.getUnitNumber(), datasBean.getHouseNumber(), datasBean.getCommunityName(), datasBean.getCommunityid(), datasBean.getDistrict(), datasBean.getDistrictName()));
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

    @OnClick({R.id.tll_pay_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tll_pay_record:
                IntentUtils.startActivity(this, PayRecordListActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
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
        getResidentList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, true);
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
                    getResidentList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, false);
                }
            }, 500);
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
//                    mXrefreshview.setLoadComplete(true);
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

}
