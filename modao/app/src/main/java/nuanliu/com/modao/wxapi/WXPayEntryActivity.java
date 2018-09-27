package nuanliu.com.modao.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.ui.activity.PaySuccessActivity;
import nuanliu.com.modao.utils.IntentUtils;

/**
 * Author Created by zhongtao
 * Date  2018-06-20
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private String WX_APP_ID = Fields.WX_ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

//    @Override
//    protected int getLayoutId() {
//        return R.layout.pay_result;
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(final BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String tip = "";
            if (resp.errCode == 0) {
                tip = "支付成功";
                IntentUtils.startActivity(WXPayEntryActivity.this, PaySuccessActivity.class);
//                Intent intent = new Intent();
//                intent.setAction("pay");
//                sendBroadcast(intent);
            } else if (resp.errCode == -1) {
                tip = "支付失败";
            } else {
                tip = "支付取消";
            }

//            Log.d(this.getClass().getSimpleName() + "微信支付结果", tip);
            Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }


}