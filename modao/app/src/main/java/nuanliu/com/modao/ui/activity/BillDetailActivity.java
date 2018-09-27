package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.OrderDetailBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 账单详情
 */

public class BillDetailActivity extends BaseActivity {

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_pay_amount)
    TextView tvPayAmount;
    @BindView(R.id.tv_pay_state)
    TextView tvPayState;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_tip)
    TextView tvPayTip;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_pay_address)
    TextView tvPayAddress;
    @BindView(R.id.tv_heat_year)
    TextView tvHeatYear;
    @BindView(R.id.tv_total_owe)
    TextView tvTotalOwe;
    @BindView(R.id.tv_should_pay)
    TextView tvShouldPay;
    @BindView(R.id.tv_cut_amount)
    TextView tvCutAmount;
    @BindView(R.id.tv_real_pay)
    TextView tvRealPay;
    @BindView(R.id.tv_pay_date)
    TextView tvPayDate;
    @BindView(R.id.tv_trading_number)
    TextView tvTradingNumber;

    private OrderDetailBean bean;
    private SimpleDateFormat simpleDateFormat;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill_detail;
    }

    private void initView() {
        getToolBar().setTitle("帐单详情");
    }

    private void initData() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (null != getIntent()) {
            if (getIntent().hasExtra(Fields.BILL_ID)) {
                id = getIntent().getStringExtra(Fields.BILL_ID);
            }
        }
        orderDetail(id);
    }

    private void orderDetail(String id) {
        showLoading();
        ServiceFactory
                .getApis()
                .orderDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<OrderDetailBean>>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse<OrderDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<OrderDetailBean> orderDetailBeanBaseResponse) {
                        if (orderDetailBeanBaseResponse.getStatus() == 1) {
                            bean = orderDetailBeanBaseResponse.getBody();
                            setData(bean);
                        }
                    }
                });
    }

    private void setData(OrderDetailBean item) {
        OrderDetailBean.DataBean data = item.getData();
        tvCompanyName.setText(data.getCompanyName());
        tvPayAmount.setText("-" + String.valueOf(data.getAmount()));
        tvPayType.setText(data.getPaymethod());
        tvPayTip.setText(data.getTitle_());
        tvCardNumber.setText(data.getAccount_h());
        tvUserName.setText(data.getHouseholder());
        tvPayAddress.setText(data.getAddre());
        tvHeatYear.setText(data.getYear());
        tvTotalOwe.setText(data.getTotal_qf() + "元");
        tvShouldPay.setText(data.getPaidChargeMoney() + "元");
        tvCutAmount.setText(data.getRemission() + "元");
        tvRealPay.setText(String.valueOf(data.getAmount()) + "元");
        tvPayDate.setText(simpleDateFormat.format(new Date(data.getTime())));
        tvTradingNumber.setText(data.getBizId());
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}
