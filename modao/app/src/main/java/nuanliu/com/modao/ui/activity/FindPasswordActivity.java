package nuanliu.com.modao.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import nuanliu.com.modao.R;
import nuanliu.com.modao.base.BaseActivity;
import nuanliu.com.modao.base.BaseResponse;
import nuanliu.com.modao.bean.CodeBean;
import nuanliu.com.modao.network.ServiceFactory;
import nuanliu.com.modao.utils.IntentUtils;
import nuanliu.com.modao.widget.ClearEditText;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 找回密码
 */

public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.cet_user_phone)
    ClearEditText cetUserPhone;
    @BindView(R.id.cet_verify_code)
    ClearEditText cetVerifyCode;
    @BindView(R.id.cet_new_password)
    ClearEditText cetNewPassword;
    @BindView(R.id.cet_confirm_password)
    ClearEditText cetConfirmPassword;

    private String telPhone;
    private String veriCode;
    private String newPasswd;
    private String confirmPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_password;
    }

    private void initView() {
        getToolBar().setTitle("找回密码");
    }

    private void initData() {
        telPhone = cetUserPhone.getText().toString().trim();
        veriCode = cetVerifyCode.getText().toString().trim();
        newPasswd = cetNewPassword.getText().toString().trim();
        confirmPasswd = cetConfirmPassword.getText().toString().trim();
    }

    @OnClick({R.id.tv_get_verify_code, R.id.btn_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verify_code:
                telPhone = cetUserPhone.getText().toString().trim();
                if (null != telPhone) {
                    Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    getVeriCode(telPhone);
                }
                break;
            case R.id.btn_complete:
                initData();
                if (newPasswd.equals(confirmPasswd)) {
                    findPasswd(telPhone, newPasswd, veriCode);
                } else {
                    Toast.makeText(this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void findPasswd(String telPhone, String newPasswd, String veriCode) {
        ServiceFactory
                .getApis()
                .uppwdbyTelphone(telPhone, newPasswd, veriCode)
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
                    public void onNext(BaseResponse userBaseResponse) {
                        if (userBaseResponse.getStatus() == 1) {
                            Toast.makeText(FindPasswordActivity.this, "找回并重置密码成功", Toast.LENGTH_SHORT).show();
                            IntentUtils.startActivity(FindPasswordActivity.this, LoginActivity.class);
                        } else {
                            Toast.makeText(FindPasswordActivity.this, "找回密码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getVeriCode(String telphone) {
        ServiceFactory
                .getApis()
                .getVeriCode(telphone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<CodeBean>>bindToLifecycle())
                .subscribe(new Subscriber<BaseResponse<CodeBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseResponse<CodeBean> codeBeanBaseResponse) {
                        if (codeBeanBaseResponse.getStatus() == 1) {
                            veriCode = codeBeanBaseResponse.getBody().getCode();
                        }
                    }
                });
    }
}
