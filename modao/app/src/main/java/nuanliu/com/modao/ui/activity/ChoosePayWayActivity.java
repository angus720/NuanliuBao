package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.alipay.AlipayManager;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.PayBean;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.utils.IntentUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 选择缴费方式
 */

public class ChoosePayWayActivity extends BaseActivity {

    @BindView(R.id.wechat_pay_check)
    ImageView ivWechatPay;
    @BindView(R.id.alipay_check)
    ImageView ivAlipay;
    @BindView(R.id.cloud_flash_pay_check)
    ImageView ivCloudFlashPay;
    @BindView(R.id.tv_payment_amount)
    TextView tvPaymentAmount;
    @BindView(R.id.tv_payment_type)
    TextView tvPaymentType;
    @BindView(R.id.pay_amount)
    TextView payAmount;

    public static final String WEIXIN_PAY = "1";//微信支付
    public static final String ALIPAY = "2";//支付宝支付
    public static final String CLOUD_FLASH_PAY = "3";//云闪付
    private String type = WEIXIN_PAY;
    private String residentId;
    private String sjje;//实付金额
    private String year;
    private String invoiceType;

    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private final String mMode = "00";
//    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_pay_way;
    }

    private void initView() {
        getToolBar().setTitle("选择缴费方式");
    }

    private void initData() {
        if (null != getIntent()) {
            if (getIntent().hasExtra(Fields.R_ID)) {
                residentId = getIntent().getStringExtra(Fields.R_ID);
            }
            if (getIntent().hasExtra(Fields.SJJE)) {
                sjje = getIntent().getStringExtra(Fields.SJJE);
            }
            if (getIntent().hasExtra(Fields.YEAR)) {
                year = getIntent().getStringExtra(Fields.YEAR);
            }
            if (getIntent().hasExtra(Fields.INVOICE_TYPE)) {
                invoiceType = getIntent().getStringExtra(Fields.INVOICE_TYPE);
            }
        }

        tvPaymentAmount.setText("¥ " + sjje);
        payAmount.setText(sjje);
    }

    @OnClick({R.id.rl_wechat_pay, R.id.rl_alipay, R.id.rl_cloud_flash_pay, R.id.confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_wechat_pay:
                type = WEIXIN_PAY;
                changeImage(ivWechatPay, ivAlipay, ivCloudFlashPay);
                break;
            case R.id.rl_alipay:
                type = ALIPAY;
                changeImage(ivAlipay, ivWechatPay, ivCloudFlashPay);
                break;
            case R.id.rl_cloud_flash_pay:
                type = CLOUD_FLASH_PAY;
                changeImage(ivCloudFlashPay, ivWechatPay, ivAlipay);
                break;
            case R.id.confirm_pay:
                pay(residentId, year, type, "1", invoiceType);
                break;
        }
    }

    private void pay(String residentid, String year, String flag, String entrance, String invoiceType) {
        ServiceFactory
                .getApis()
                .getPay(residentid, year, flag, entrance, invoiceType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<PayBean>>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse<PayBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<PayBean> baseResponse) {
                        if (baseResponse.getStatus() == 1) {
                            if (type == WEIXIN_PAY) {
                                wxPay(baseResponse.getBody());
                            } else if (type == ALIPAY) {
                                new AlipayManager(ChoosePayWayActivity.this, baseResponse.getBody().getParams());
                            } else if (type == CLOUD_FLASH_PAY) {
                                UPPayAssistEx.startPayByJAR(ChoosePayWayActivity.this, PayActivity.class, null, null, baseResponse.getBody().getParams(), mMode);
                            }
                        }
                    }
                });
    }

    private void wxPay(PayBean payBean) {
        IWXAPI api = WXAPIFactory.createWXAPI(this, Fields.WX_ID);
        if (!api.isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        api.registerApp(Fields.WX_ID);
        PayReq req = new PayReq();
        req.appId = Fields.WX_ID;
        req.partnerId = payBean.getPartnerid();
        req.prepayId = payBean.getPrepayid();
        req.nonceStr = payBean.getNoncestr();
        req.timeStamp = payBean.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = payBean.getSign();
        api.sendReq(req);
    }

    private void changeImage(ImageView iv1, ImageView iv2, ImageView iv3) {
        iv1.setImageResource(R.mipmap.pay_choose);
        iv2.setImageResource(R.mipmap.pay_unchoose);
        iv3.setImageResource(R.mipmap.pay_unchoose);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            IntentUtils.startActivity(ChoosePayWayActivity.this, PaySuccessActivity.class);
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
