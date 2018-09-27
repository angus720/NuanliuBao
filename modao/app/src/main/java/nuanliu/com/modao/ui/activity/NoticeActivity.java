package nuanliu.com.modao.ui.activity;

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
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.bean.NoticeBean;
import nuanliu.com.modao.bean.NoticeListBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.NoticeAdapter;
import nuanliu.com.modao.widget.refreshview.XRefreshView;
import nuanliu.com.modao.widget.refreshview.XRefreshViewFooter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 通知公告
 */

public class NoticeActivity extends BaseActivity implements XRefreshView.XRefreshViewListener {

    @BindView(R.id.rv_recyclerview)
    RecyclerView mRvRecyclerview;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    @BindView(R.id.multiplestatusview_all)
    MultipleStatusView mMultiplestatusview;
    @BindView(R.id.xrefreshview)
    XRefreshView mXrefreshview;

    private List<NoticeBean> mDatas = new ArrayList<>();
    private NoticeAdapter<NoticeBean> mAdapter;

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
        return R.layout.activity_notice;
    }

    private void initView() {
        getToolBar().setTitle("通知公告");
        mXrefreshview.setPinnedTime(1000);
        mXrefreshview.setMoveForHorizontal(true);
        mXrefreshview.setPullLoadEnable(true);
        mXrefreshview.setAutoLoadMore(false);

        mXrefreshview.enableReleaseToLoadMore(true);
        mXrefreshview.enableRecyclerViewPullUp(true);
        mXrefreshview.enablePullUpWhenLoadCompleted(true);
        mXrefreshview.setXRefreshViewListener(this);

        mAdapter = new NoticeAdapter<>(this);
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
        mMultiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnnouncement(true);
            }
        });
        getAnnouncement(true);
    }

    private void getAnnouncement(final boolean isrefresh) {
        ServiceFactory
                .getApis()
                .getAnnouncement("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<NoticeListBean>bindToLifecycle())
                .subscribe(new Subscriber<NoticeListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NoticeListBean noticeListBean) {
                        if (noticeListBean.getStatus() == 1) {
                            refreshViewByData(noticeListBean.getBody().getDatas(), isrefresh);
                        }
                    }
                });
    }

    private void refreshViewByData(List<NoticeListBean.BodyBean.DatasBean> list, final boolean isrefresh) {
        mDatas.clear();
        if (list == null || list.size() == 0) {

            mXrefreshview.setLoadComplete(true);

            if (isrefresh) {
                mMultiplestatusview.showEmpty();
            }

            return;
        }
        for (int i = 0; i < list.size(); i++) {
            NoticeListBean.BodyBean.DatasBean datasBean = list.get(i);
            mDatas.add(new NoticeBean(datasBean.getTitle(), datasBean.getCreateTime(), datasBean.getContent()));
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
        getAnnouncement(true);
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
                    getAnnouncement(false);
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
