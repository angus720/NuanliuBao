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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.AddressItemBean;
import nuanliu.com.modao.bean.AddressListBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.MyAddressAdapter;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import nuanliu.com.modao.widget.refreshview.XRefreshViewFooter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 邮寄地址
 */

public class MyAddressActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    @BindView(R.id.multiplestatusview_all)
    MultipleStatusView mMultiplestatusview;
    @BindView(R.id.xrefreshview)
    XRefreshView mXrefreshview;

    private List<AddressItemBean> mDatas = new ArrayList<>();
    private MyAddressAdapter<AddressItemBean> mAdapter;

    private int mStartPage = 1;
    private int mPagesize = 10;

    private boolean menableLoadmore = false;

    private int flag;

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
        return R.layout.activity_my_address;
    }

    private void initEvent() {
        mAdapter.setOnItemClickListener(new MyAddressAdapter.ItemClickListener() {
            @Override
            public void onItemClick(AddressItemBean item, int positon) {
                if (flag == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("mailAddress", item);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void initView() {
        getToolBar().setTitle("邮寄地址");
        mXrefreshview.setPinnedTime(1000);
        mXrefreshview.setMoveForHorizontal(true);
        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);

        mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        mXrefreshview.setXRefreshViewListener(this);

        mAdapter = new MyAddressAdapter<>(this);
        mAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
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
            flag = getIntent().getIntExtra("flag", 0);
        }

        mMultiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddressList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, true);
            }
        });

        getAddressList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, true);
    }

    private void getAddressList(String userName, int startPage, int pageSize, final boolean isrefresh) {
        showLoading("加载中");
        mMultiplestatusview.showContent();
        ServiceFactory
                .getApis()
                .getAddressList(userName, startPage, pageSize)
                .delaySubscription(510, TimeUnit.MICROSECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<AddressListBean>bindToLifecycle())
                .subscribe(new Subscriber<AddressListBean>() {
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
                    public void onNext(AddressListBean addressListBean) {
                        if (addressListBean.getStatus() == 1) {
                            refreshViewByData(addressListBean.getBody().getList(), isrefresh);
                        } else {
                            mMultiplestatusview.showEmpty();
                        }
                    }
                });
    }

    private void refreshViewByData(List<AddressListBean.BodyBean.ListBean> list, boolean isrefresh) {
        mDatas.clear();
        if (list == null || list.size() == 0) {

            mXrefreshview.setLoadComplete(true);

            if (isrefresh) {
                mMultiplestatusview.showEmpty();
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
            AddressListBean.BodyBean.ListBean datasBean = list.get(i);
            mDatas.add(new AddressItemBean(datasBean.getIsDefault(), datasBean.getCity(), datasBean.getDetailed(), datasBean.getProvice(),
                    datasBean.getDistrict(), datasBean.getTelphone(), datasBean.getContacts(), datasBean.getId(), datasBean.getCustomerUserid()));
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

    @OnClick(R.id.tll_add_new)
    public void onClick() {
        IntentUtils.startActivity(this, AddressEditActivity.class);
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
        getAddressList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, true);
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
                    getAddressList(SpUtil.getUser().getUsername(), mStartPage, mPagesize, false);
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
