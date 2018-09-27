package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.utils.IntentUtils;

/**
 * 地址管理
 */

public class AddressManagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

    private void initView() {
        getToolBar().setTitle("地址管理");
    }

    @OnClick({R.id.trl_repair_address, R.id.trl_mail_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_repair_address:
                IntentUtils.startActivity(this, RepairAddressActivity.class);
                break;
            case R.id.trl_mail_address:
                IntentUtils.startActivity(this, MyAddressActivity.class);
                break;
        }
    }
}
