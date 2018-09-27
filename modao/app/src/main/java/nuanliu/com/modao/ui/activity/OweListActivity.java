package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.OweItemBean;
import nuanliu.com.modao.bean.OweListBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.OweListAdapter;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 欠费清单
 */

public class OweListActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    @BindView(R.id.multiplestatusview_all)
    MultipleStatusView mMultiplestatusview;
    @BindView(R.id.xrefreshview)
    XRefreshView mXrefreshview;
    @BindView(R.id.tv_total_owe)
    TextView tvTotalOwe;

    private List<OweItemBean> mDatas = new ArrayList<>();
    private OweListAdapter<OweItemBean> mAdapter;
    private String residentId;

    private int mStartPage = 1;
    private int mPagesize = 10;

    private boolean menableLoadmore = false;

    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owe_list;
    }

    private void initView() {
        getToolBar().setTitle("欠费清单");
        mXrefreshview.setPinnedTime(1000);
        mXrefreshview.setMoveForHorizontal(true);
//        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);

        mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        mXrefreshview.setXRefreshViewListener(this);

        mAdapter = new OweListAdapter<>(this);
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
        if (null != getIntent()) {
            if (getIntent().hasExtra(Fields.RESIDENT_ID)) {
                residentId = getIntent().getStringExtra(Fields.RESIDENT_ID);
            }
        }

        mMultiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOweList(SpUtil.getUser().getTelphone(), residentId, true);
            }
        });

        getOweList(SpUtil.getUser().getTelphone(), residentId, true);
    }

    private void getOweList(String telphone, String residentId, final boolean isrefresh) {
        showLoading("加载中");
        ServiceFactory
                .getApis()
                .getOweList(telphone, residentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<OweListBean>bindToLifecycle())
                .subscribe(new Subscriber<OweListBean>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(OweListBean oweListBean) {
                        if (oweListBean.status == 1) {
                            tvTotalOwe.setText("￥" + oweListBean.getBody().getTotal_a());
                            refreshViewByData(oweListBean.getBody().getList(), isrefresh);
                        } else {
                            mMultiplestatusview.showEmpty();
                        }
                    }
                });
    }

    private void refreshViewByData(List<OweListBean.BodyBean.ListBean> list, boolean isrefresh) {
        mDatas.clear();
        if (list == null || list.size() == 0) {

            mXrefreshview.setLoadComplete(true);

            if (isrefresh) {
                mMultiplestatusview.showEmpty();
            }

            return;
        }
        for (int i = 0; i < list.size(); i++) {
            OweListBean.BodyBean.ListBean dataBean = list.get(i);
            mDatas.add(new OweItemBean(dataBean.getYear(), dataBean.getUseArea(), dataBean.getPrice(), dataBean.getShouldPay(), dataBean.getSimoney(), dataBean.getRemission(),
                    dataBean.getConcession(), dataBean.getArrearsDays(), dataBean.getLateFee_t(), dataBean.getKnots(), dataBean.getTotal_c()));
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
        getOweList(SpUtil.getUser().getTelphone(), residentId, true);
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
                    getOweList(SpUtil.getUser().getTelphone(), residentId, false);
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
}
