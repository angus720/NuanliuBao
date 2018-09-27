package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.RepairOrderDetailBean;
import nuanliu.com.modao.bean.RepairOrderProgressBean;
import nuanliu.com.modao.bean.RepairScheduleBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.adapter.RepairScheduleAdapter;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.utils.DateUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 工单详情
 */

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.repair_progress)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_order_content)
    TextView tvOrderContent;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_contact_phone)
    TextView tvPhone;
    @BindView(R.id.tv_repair_addr)
    TextView tvAddr;
    @BindView(R.id.tv_appoint_time)
    TextView tvAppointTime;
    @BindView(R.id.tv_repair_status)
    TextView tvRepairStatus;
    @BindView(R.id.btn_status)
    Button btnStatus;
    @BindView(R.id.ll_submitted_pictures)
    LinearLayout mllPicture;
    @BindView(R.id.iv_one)
    ImageView mIvOne;
    @BindView(R.id.iv_two)
    ImageView mIvTwo;
    @BindView(R.id.iv_three)
    ImageView mIvThree;

    private RepairScheduleAdapter<RepairScheduleBean> mAdapter;
    private String mOrderid;
    private String mOrderAddr;
    private int mCategory;
    private List mArrayList = new ArrayList();
    private ArrayList<String> mPicturesfinishList = new ArrayList<>();
    private RepairOrderDetailBean.BodyBean mBodyBean;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    private void initView() {
        getToolBar().setTitle("工单详情");
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {

        if (null != getIntent()) {
            mOrderid = getIntent().getStringExtra("orderId");
            mOrderAddr = getIntent().getStringExtra("orderAddr");
            mCategory = getIntent().getIntExtra("category", -1);
        }
        getOrderDetail(mOrderid);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderProgress(mOrderid);
            }
        }, 500);
    }

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.btn_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_one:
            case R.id.iv_two:
            case R.id.iv_three:
                Intent intent1 = new Intent(this, PhotoViewViewpageActivity.class);
                intent1.putExtra(Fields.PHOTO_VIEW, mPicturesfinishList);
                startActivity(intent1);
                break;
            case R.id.btn_status:
                switch (mBodyBean.getStatus()) {
                    case 0:
                        new AppDialog(this).builder()
                                .setTitle("温馨提示")
                                .setMsg("报修单已形成，确认撤销工单？")
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cancelOrder(mOrderid, "", "用户取消");
                                    }
                                }).show();
                        break;
                    case 1:
                    case 2:
                        break;
                    case 3:
                        Intent intent = new Intent(this, RepairEvaluationActivity.class);
                        intent.putExtra("orderId", mOrderid);
                        intent.putExtra("orderAddr", mOrderAddr);
                        intent.putExtra("category", mCategory);
                        startActivity(intent);
                        break;
                    case 4:
                    case 5:
                        break;
                }
                break;
        }
    }

    private void getOrderDetail(String orderId) {
        showLoading("加载中");
        ServiceFactory
                .getRepairapis()
                .getOrderDetail(orderId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<RepairOrderDetailBean>bindToLifecycle())
                .subscribe(new Subscriber<RepairOrderDetailBean>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(RepairOrderDetailBean repairOrderDetailBean) {
                        if (repairOrderDetailBean.getStatus() == 1) {
                            refreshViewByData(repairOrderDetailBean);
                        }
                    }
                });
    }

    private void cancelOrder(String orderId, String description, String cancelType) {
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
                            finish();
                        }
                    }
                });
    }

    private void getOrderProgress(String orderId) {
        showLoading();
        ServiceFactory
                .getRepairapis()
                .getOrderProgress(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<RepairOrderProgressBean>bindToLifecycle())
                .subscribe(new Subscriber<RepairOrderProgressBean>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(RepairOrderProgressBean repairOrderProgressBean) {
                        if (repairOrderProgressBean.getStatus() == 1) {
                            mAdapter = new RepairScheduleAdapter(OrderDetailActivity.this, repairOrderProgressBean.getBody().getPostion());
                            mRecyclerview.setAdapter(mAdapter);
                            getProgressData(repairOrderProgressBean.getBody().getProgress());
                        }
                    }
                });
    }

    private void getProgressData(List<RepairOrderProgressBean.BodyBean.ProgressBean> beanList) {
        if (null != beanList) {
            Collections.reverse(beanList);
            for (int i = 0; i < beanList.size(); i++) {
                RepairOrderProgressBean.BodyBean.ProgressBean bean = beanList.get(i);
                mArrayList.add(new RepairScheduleBean(bean.getName(), DateUtil.stringToType(bean.getTime() + "", "yyyy-MM-dd HH:mm:ss"), bean.getDetail()));
            }
            mAdapter.addData(mArrayList);
        }
    }

    /**
     * 刷新界面
     */
    private void refreshViewByData(RepairOrderDetailBean repairOrderDetailBean) {
        mBodyBean = repairOrderDetailBean.getBody();

        switch (mBodyBean.getCategory()) {
            case 0:
                tvOrderContent.setText("暖气不热");
                break;
            case 1:
                tvOrderContent.setText("漏电");
                break;
            case 2:
                tvOrderContent.setText("漏水");
                break;
            case 3:
                tvOrderContent.setText("其他");
                break;
        }

        tvContact.setText(mBodyBean.getContacts());
        tvPhone.setText(mBodyBean.getTelephone());
        tvAddr.setText(mBodyBean.getAddress());
        tvAppointTime.setText("");
        switch (mBodyBean.getStatus()) {
            case 0:
                tvRepairStatus.setText("待处理");
                break;
            case 1:
            case 2:
                tvRepairStatus.setText("处理中");
                btnStatus.setText("正在处理中...");
//                btnStatus.setVisibility(View.GONE);
                btnStatus.setClickable(false);
                break;
            case 3:
                tvRepairStatus.setText("已完工");
                btnStatus.setText("评价");
                break;
            case 4:
            case 5:
                tvRepairStatus.setText("已完结");
                btnStatus.setText("维修已完成");
                btnStatus.setClickable(false);
                break;
        }

        //有图片显示图片  moment 0  和 2
        List<RepairOrderDetailBean.BodyBean.CommentsBean> comments = mBodyBean.getComments();

        if (comments != null && mBodyBean.getComments().size() > 0) {

            for (int i = 0; i < comments.size(); i++) {
                String pictures = comments.get(i).getPictures();
                if (pictures != null && !pictures.equals("")) {
                    String[] split = pictures.split(",");

                    for (int i1 = 0; i1 < split.length; i1++) {
                        if (!split[i1].equals("")) {
                            mPicturesfinishList.add(split[i1]);
                        }
                    }
                }
            }
        }

        if (mPicturesfinishList.size() == 0) {
            mllPicture.setVisibility(View.GONE);
        } else {
            mllPicture.setVisibility(View.VISIBLE);
            setImageViewBg(mPicturesfinishList.size() >= 1 ? mPicturesfinishList.get(0) : "", mIvOne);
            setImageViewBg(mPicturesfinishList.size() >= 2 ? mPicturesfinishList.get(1) : "", mIvTwo);
            setImageViewBg(mPicturesfinishList.size() >= 3 ? mPicturesfinishList.get(2) : "", mIvThree);
        }
    }

    private void setImageViewBg(String str, ImageView imageView) {
        Glide.with(this)
                .load(str)
                .into(imageView);
    }
}
