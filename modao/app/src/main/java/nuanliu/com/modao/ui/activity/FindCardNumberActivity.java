package nuanliu.com.modao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.constant.Fields;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.ui.dialog.CardInfoDialog;
import nuanliu.com.modao.utils.IntentUtils;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 卡号查询
 */

public class FindCardNumberActivity extends BaseActivity {

    @BindView(R.id.et_owner_name)
    EditText etOwnerName;
    @BindView(R.id.et_owner_old_number)
    EditText etOwnerOldNumber;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;

    private CardInfoDialog cardInfoDialog;

    private static final int REQUEST_HEAT_COMPANY = 100;

    private String mCompanyName;
    private String mCompanyId;
    private String mOwnerName;
    private String mOwnerOldNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_card_number;
    }

    private void initView() {
        getToolBar().setTitle("卡号查询");
    }

    private void initData() {
        mOwnerName = etOwnerName.getText().toString().trim() + "";
        mOwnerOldNumber = etOwnerOldNumber.getText().toString().trim() + "";
    }

    @OnClick({R.id.trl_choose_company, R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            //选择供热公司
            case R.id.trl_choose_company:
                Intent intent = new Intent(this, ChooseCompanyActivity.class);
                startActivityForResult(intent, REQUEST_HEAT_COMPANY);
                break;
            case R.id.btn_confirm:
                showDialog();
//                initData();
//                bdHouse(SpUtil.getUser().getUsername(), SpUtil.getUser().getTelphone(), mCompanyId, mOwnerName, mOwnerOldNumber);
                break;
        }
    }

    private void bdHouse(String userName, String telPhone, String companyId, String houseHolder, String account_h) {
        ServiceFactory
                .getApis()
                .bdHouse(userName, telPhone, companyId, houseHolder, account_h)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse>bindToLifecycle())
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
                            Toast.makeText(FindCardNumberActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                            showDialog();
                        } else {
                            Toast.makeText(FindCardNumberActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showDialog() {
        cardInfoDialog = new CardInfoDialog(this).builder();
        cardInfoDialog.setUserName("angus");
        cardInfoDialog.setNewNumber("6655555");
        cardInfoDialog.setCommunityName("海棠湾");
        cardInfoDialog.setBuildingNumber("2#");
        cardInfoDialog.setHouseNumber("1104室");
        cardInfoDialog.setNegativeButton("返回修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardInfoDialog.setPositiveButton("确认绑定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startActivity(FindCardNumberActivity.this, OnlinePaymentActivity.class);
            }
        });
        cardInfoDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_HEAT_COMPANY:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra(Fields.CHOOSE_COMPANY)) {
                        mCompanyName = data.getStringExtra(Fields.CHOOSE_COMPANY);
                        tvCompanyName.setText(mCompanyName);
                    }
                    if (data.hasExtra(Fields.COMPANY_ID)) {
                        mCompanyId = data.getStringExtra(Fields.COMPANY_ID);
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(cardInfoDialog != null) {
            cardInfoDialog.dismiss();
        }
        super.onDestroy();
    }
}
