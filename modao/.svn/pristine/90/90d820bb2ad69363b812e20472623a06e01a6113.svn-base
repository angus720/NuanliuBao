package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.ui.MainActivity;
import nuanliu.com.modao.utils.IntentUtils;

/**
 * 支付成功
 */

public class PaySuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    private void initView() {
        getToolBar().setTitle("支付成功");
    }

    private void initEvent() {
        getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(PaySuccessActivity.this, MainActivity.class);
            }
        });
    }

    @OnClick({R.id.btn_complete, R.id.btn_view_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_complete:
                IntentUtils.startActivity(this, MainActivity.class);
                break;
            case R.id.btn_view_order:
                IntentUtils.startActivity(this, PayRecordListActivity.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtils.startActivity(this, MainActivity.class);
    }
}
