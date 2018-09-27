package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.CouponBean;
import nuanliu.com.modao.bean.ShouldPayBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.dialog.AppDialog;
import nuanliu.com.modao.ui.dialog.CouponDialog;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.utils.SpUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 缴费详情
 */

public class PaymentDetailActivity extends BaseActivity {

    @BindView(R.id.tv_heating_season)
    TextView tvHeatingSeason;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_owner_name)
    TextView tvOwnerName;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_heating_area)
    TextView tvHeatingArea;
    @BindView(R.id.tv_heating_price)
    TextView tvHeatingPrice;
    @BindView(R.id.tv_current_owe)
    TextView tvCurrentOwe;
    @BindView(R.id.tv_coupons)
    TextView tvCoupons;
    @BindView(R.id.total_count)
    TextView totalCount;
    @BindView(R.id.tv_invoice_info)
    TextView tvInvoiceInfo;
    @BindView(R.id.payable_amount)
    TextView payableAmount;
    @BindView(R.id.real_pay_amount)
    TextView realPayAmount;
    @BindView(R.id.tv_heat_amount)
    TextView tvHeatAmount;
    @BindView(R.id.tv_discount_amount)
    TextView tvDiscountAmount;
    @BindView(R.id.tv_cut_amount)
    TextView tvCutAmount;
    @BindView(R.id.tv_previous_amount)
    TextView tvPreviousAmount;

    private List<CouponBean> couponBeanList;
    private CouponBean couponBean;

    private CouponDialog couponDialog;
    private ShouldPayBean bean;
    private String residentId;
    private String companyName;
    private String isBlackUser;
    private String invoiceType = "1";//1：不开发票 2：纸质发票 3：电子发票
    private AppDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_detail;
    }

    private void initView() {
        getToolBar().setTitle("缴费详情");
    }

    private void initData() {
        if (null != getIntent()) {
            if (getIntent().hasExtra(Fields.RESIDENT_ID)) {
                residentId = getIntent().getStringExtra(Fields.RESIDENT_ID);
            }
            if (getIntent().hasExtra(Fields.COMPANY_NAME)) {
                companyName = getIntent().getStringExtra(Fields.COMPANY_NAME);
            }
        }
        shouldPayDetail(SpUtil.getUser().getUsername(), residentId);
    }

    private void shouldPayDetail(String username, String residentId) {
        showLoading();
        ServiceFactory
                .getApis()
                .shouldPayInfo(username, residentId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(this.<BaseResponse<ShouldPayBean>>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse<ShouldPayBean>>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<ShouldPayBean> shouldPayBeanBaseResponse) {
                        if (shouldPayBeanBaseResponse.getStatus() == 1) {
                            bean = shouldPayBeanBaseResponse.getBody();
                            setData(bean);
                        }
                    }
                });
    }

    private void setData(ShouldPayBean item) {
        tvCompanyName.setText(companyName);
        tvCardNumber.setText(item.getAccount_h());
        tvOwnerName.setText(item.getHouseholder());
        tvAddressDetail.setText(item.getAddre());
        tvHeatingSeason.setText(item.getYear_());
        tvHeatingArea.setText(item.getUsableArea() + "㎡");
        tvHeatingPrice.setText(item.getPrice() + "元/㎡");
        tvCurrentOwe.setText(item.getDqqf() + "元");
        totalCount.setText("￥" + item.getSjje());
        payableAmount.setText("￥" + item.getShouldpay());
        realPayAmount.setText("￥" + item.getSjje());
        isBlackUser = item.getIsBlackUser();
        tvHeatAmount.setText(item.getHeat_fee() + "元");
        tvDiscountAmount.setText(item.getChargeFree() + "元");
        tvCutAmount.setText(item.getYjm() + "元");
        tvPreviousAmount.setText(item.getKnots() + "元");
    }

    @OnClick({R.id.tll_pay_record, R.id.trl_current_owe, R.id.trl_choose_coupon, R.id.trl_invoice_info, R.id.immediate_payment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tll_pay_record:
                IntentUtils.startActivity(this, PayRecordListActivity.class);
                break;
            case R.id.trl_current_owe:
                if (0 == Double.parseDouble(bean.getDqqf())) {
                    Toast.makeText(this, "暂无欠费", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, OweListActivity.class);
                    intent.putExtra(Fields.RESIDENT_ID, residentId);
                    startActivity(intent);
                }
                break;
            case R.id.trl_choose_coupon:
                Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show();
//                if (null != couponBeanList && couponBeanList.size() > 0) {
//                    couponDialog = new CouponDialog(this, couponBeanList, couponBean.getCouponId());
//                    couponDialog.setCouponSelectListener(new CouponDialog.couponSelectListener() {
//                        @Override
//                        public void chooseCoupon(int pos) {
//                            couponBean = couponBeanList.get(pos);
//                        }
//                    });
//                    couponDialog.show();
//                }
                break;
            case R.id.trl_invoice_info:
                new AppDialog(this).builder()
                        .setTitle("温馨提示")
                        .setMsg("系统目前只支持纸质发票,\n请自行前往营业厅办理")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvInvoiceInfo.setText("不开具发票");
                                invoiceType = "1";
                            }
                        })
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvInvoiceInfo.setText("纸质发票");
                                invoiceType = "2";
                            }
                        }).show();
//                IntentUtils.startActivity(this, InvoiceInfoActivity.class);
                break;
            case R.id.immediate_payment:
                if (null != isBlackUser) {
                    if (isBlackUser.equals("0")) {
                        if (0 == Double.parseDouble(bean.getSjje())) {
                            Toast.makeText(this, "无需支付", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(PaymentDetailActivity.this, ChoosePayWayActivity.class);
                            intent.putExtra(Fields.R_ID, bean.getRid());
                            intent.putExtra(Fields.SJJE, bean.getSjje());
                            intent.putExtra(Fields.YEAR, bean.getYear_());
                            intent.putExtra(Fields.INVOICE_TYPE, invoiceType);
                            startActivity(intent);
                        }
                    } else {
                        mDialog = new AppDialog(this).builder()
                                .setTitle(getResources().getString(R.string.warm_prompt))
                                .setMsg(getResources().getString(R.string.prompt_message))
                                .setNegativeButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setPositiveButton("确认", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        IntentUtils.startActivity(PaymentDetailActivity.this, OnlinePaymentActivity.class);
                                    }
                                });
                        mDialog.show();
                    }
                }
                break;
        }
    }
}
