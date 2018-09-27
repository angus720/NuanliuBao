package nuanliu.com.modao.ui.fragment;


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
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseFragment;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.MaintenanceOrderBean;
import nuanliu.com.modao.bean.OrderListBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.activity.NewOrderActivity;
import nuanliu.com.modao.ui.adapter.RepariOrderAdapter;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import nuanliu.com.modao.widget.refreshview.XRefreshViewFooter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 全部工单Fragment
 */
public class AllOrderFragment extends BaseFragment implements XRefreshView.XRefreshViewListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    @BindView(R.id.multiplestatusview_all)
    MultipleStatusView mMultiplestatusview;
    @BindView(R.id.xrefreshview)
    XRefreshView mXrefreshview;

    private List<MaintenanceOrderBean> mDatas = new ArrayList<>();
    private RepariOrderAdapter<MaintenanceOrderBean> mAdapter;
    private final LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

    private int mCursor = 0;
    private int mPagesize = 6;

    private boolean menableLoadmore = false;

    private int mloadmorecount = 1;

    private boolean isFirst = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_all_order;
    }

    @Override
    protected void logicalPro() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mXrefreshview.setPinnedTime(1000);
        mXrefreshview.setMoveForHorizontal(true);
        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);

        mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        mXrefreshview.setXRefreshViewListener(this);

        mAdapter = new RepariOrderAdapter<>(getActivity());
        mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        mRvRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                getOrderListData(0, mCursor, mPagesize, true);
            }
        });
        getOrderListData(0, mCursor, mPagesize, true);
    }

    private void initEvent() {
        mAdapter.onCancleClick(new RepariOrderAdapter.CancelOrderListner() {
            @Override
            public void onCancel(String orderId, String description, String cancelType, int position) {
                cancelOrder(orderId, description, cancelType, position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            onRefresh(true);
        }
        isFirst = false;
    }

    private void getOrderListData(int sheet, int cursor, int pagesize, final boolean isrefresh) {
        showLoading("加载中");
        mMultiplestatusview.showContent();
        ServiceFactory
                .getRepairapis()
                .getOrderList(sheet, cursor, pagesize)
                .delaySubscription(510, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<OrderListBean>bindToLifecycle())
                .subscribe(new Subscriber<OrderListBean>() {
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
                    public void onNext(OrderListBean orderListBean) {
                        if (orderListBean.getStatus() == 1) {
                            List<OrderListBean.BodyBean.DatasBean> beanList = orderListBean.getBody().getDatas();
                            if (beanList != null && beanList.size() > 0) {
                                refreshViewByData(beanList, isrefresh);
                            } else if (isrefresh) {
                                mXrefreshview.setLoadComplete(true);
                                emptyView();
                            } else {
                                mXrefreshview.setLoadComplete(true);
                            }
                        } else {
                            mMultiplestatusview.showError();
                        }
                    }
                });
    }

    private void refreshViewByData(List<OrderListBean.BodyBean.DatasBean> list, boolean isrefresh) {
        mDatas.clear();
        if (list == null || list.size() == 0) {
            mXrefreshview.setLoadComplete(true);
            if (isrefresh) {
//                mMultiplestatusview.showEmpty();
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
            OrderListBean.BodyBean.DatasBean datasBean = list.get(i);
            mDatas.add(new MaintenanceOrderBean(datasBean.getTime(), datasBean.getOrderid(), datasBean.getStatus(), datasBean.getTitle(),
                    datasBean.getProjectName(), datasBean.getType_name(), datasBean.getAddress(), datasBean.getCategory()));
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

    private void cancelOrder(String orderId, String description, String cancelType, int position) {
        ServiceFactory
                .getRepairapis()
                .cancelOrder(orderId, description, cancelType)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getStatus() == 1) {
                            onRefresh(true);
                        }
                    }
                });
    }

    private void emptyView() {
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.order_empty_view, null);
        Button btnNewOrder = (Button) emptyView.findViewById(R.id.add_new_order);
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(getActivity(), NewOrderActivity.class);
            }
        });

        mMultiplestatusview.showEmpty(emptyView, mLayoutParams);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        mXrefreshview.setLoadComplete(false);
        mloadmorecount = 1;
        menableLoadmore = true;
        mCursor = 0;
        getOrderListData(0, mCursor, mPagesize, true);
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
            mCursor = mloadmorecount * mPagesize;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    getOrderListData(0, mCursor, mPagesize, false);
                }
            }, 500);
            mloadmorecount++;
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
